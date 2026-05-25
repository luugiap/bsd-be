package com.example.bds.dto.Response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

     String id;
     String username;
     String password;
     String email;
     Set<RoleResponse> role;

}
