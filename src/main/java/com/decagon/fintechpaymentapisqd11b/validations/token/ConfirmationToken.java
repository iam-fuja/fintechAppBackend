package com.decagon.fintechpaymentapisqd11b.validations.token;

import com.decagon.fintechpaymentapisqd11b.entities.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirm_token_sequence",
            sequenceName = "confirm_token_sequence",
            allocationSize = 1
    )

    @Id

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirm_token_sequence"
    )

    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private Users user;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             Users user
    ) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
