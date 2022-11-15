package com.example.vmWare_project.service.impl;

import com.example.vmWare_project.components.impl.ValidationComponentImpl;
import com.example.vmWare_project.entity.Contact;
import com.example.vmWare_project.entity.Owner;
import com.example.vmWare_project.entity.Role;
import com.example.vmWare_project.model.RegisterRequest;
import com.example.vmWare_project.repository.ContactRepository;
import com.example.vmWare_project.repository.RoleRepository;
import com.example.vmWare_project.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final ValidationComponentImpl validationComponent;
    private final ContactRepository contactRepository;
    private final ObjectMapper mapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @SneakyThrows
    public Contact register(RegisterRequest registerRequest) {
        validationComponent.validateContactPhoneNumber(registerRequest.getPhone(), registerRequest.getEmail());
        var contact = mapper.convertValue(registerRequest, Contact.class);
        contact.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        contact.setOwner(Owner.builder().build());
        contact.setRoles(roleRepository.findAllByTypeIn(List.of(registerRequest.getRole())));

        return contactRepository.save(contact);
    }

    @Override
    public Contact getContactByMailOrPhone(String phone, String email) {
       return Objects.requireNonNull(Optional.ofNullable(phone)
                       .map(contactRepository::findContactByPhone)
                       .orElse(Optional.ofNullable(email)
                               .map(contactRepository::findContactByEmail)
                               .orElseThrow(()-> new EntityExistsException("phone and email can't be both null"))))
               .orElseThrow(()-> new EntityExistsException("phone and email can't be both null"));
    }

    @Override
    public Contact updateContact(RegisterRequest registerRequest) {
        var contact = getContactByMailOrPhone(registerRequest.getPhone(),registerRequest.getEmail());
        if (ObjectUtils.isEmpty(contact)){
            throw new EntityExistsException(String.format("%s wasn't found",registerRequest.getContactType().name().toLowerCase(Locale.ROOT)));
        }
        setContactNewValues(registerRequest, contact);
        return contactRepository.save(contact);
    }

    private void setContactNewValues(RegisterRequest registerRequest, Contact contact) {
        contact.setFirstName(Optional.ofNullable(registerRequest.getFirstName())
                            .orElse(contact.getFirstName()));
        contact.setLastName(Optional.ofNullable(registerRequest.getLastName())
                .orElse(contact.getLastName()));
        contact.setAge(Optional.ofNullable(registerRequest.getAge())
                .orElse(contact.getAge()));
        contact.setEmail(Optional.ofNullable(registerRequest.getEmail())
                .orElse(contact.getEmail()));
        contact.setPhone(Optional.ofNullable(registerRequest.getPhone())
                .orElse(contact.getPassword()));
        contact.setPassword(Optional.ofNullable(registerRequest.getPassword())
                .orElse(contact.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var contact = contactRepository.findContactByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists."));
        String[] roles = contact.getRoles().stream()
                .map(Role::getType)
                .map(current ->"ROLE_" + current)
                .toArray(String[]::new);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);

        return new User(contact.getEmail(), contact.getPassword(), authorities);
    }
}
