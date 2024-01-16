package org.example.authority;


import lombok.Getter;
import lombok.Setter;
import org.example.user.service.User;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tb_user_authority")
public class UserAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("아이디")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authority_id")
    private Authority authority;
}
