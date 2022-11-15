package com.example.vmWare_project.boot;

import com.example.vmWare_project.entity.Role;
import com.example.vmWare_project.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(value = 1)
@RequiredArgsConstructor
public class RoleBoot implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final List<String> roles = List.of("owner","customer","admin");
    @Override
    public void run(ApplicationArguments args) throws Exception {
        var entities = roleRepository.findAllByTypeIn(roles);

        if (CollectionUtils.isEmpty(entities)){
            roleRepository.saveAll(roles.stream()
                    .map(r->
                            Role.builder()
                                    .type(r)
                                    .build())
                    .collect(Collectors.toList()));
        }
    }
}
