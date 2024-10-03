package hh_week2.domain.registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "REGISTRATION")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationEntity {

    @Id
    @Column(name = "registration_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lecture_item_id")
    private Long lectureItemId;

    @Column(name = "user_id")
    private Long userId;
}