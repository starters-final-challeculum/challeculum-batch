package companion.challeculum.batch.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.entity
 */
@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "oauth_id")
    private String oauthId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "point")
    private int point;

    @Column(name = "mission_score")
    private int missionScore;

    @Column(name = "role")
    private String role;

    // constructors, getters, and setters
}

