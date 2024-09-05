package net.happykoo.aop.vo;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String userId;
    private String userPassword;
    private String userName;
}
