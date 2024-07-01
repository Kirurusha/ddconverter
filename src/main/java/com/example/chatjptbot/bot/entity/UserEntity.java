package com.example.chatjptbot.bot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="telegram_id", unique = true, nullable = false)
    private Long telegramId;
    @Column(name = "username")
    private String username;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Subscription> subscriptions;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", telegramId=" + telegramId +
                ", username='" + username + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
