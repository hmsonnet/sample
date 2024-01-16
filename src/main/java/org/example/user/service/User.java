package org.example.user.service;


import lombok.Data;
import org.hibernate.annotations.Comment;

import javax.persistence.*;


@Data
@Entity
@Table(name = "tb_user")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false, length = 50)
        @Comment("사용자 아이디")
        private String username;

        @Column(nullable = false, length = 100)
        @Comment("사용자 비밀번호")
        private String password;

        @Column(nullable = false)
        @Comment("활성화 여부")
        private Boolean enabled;

}
