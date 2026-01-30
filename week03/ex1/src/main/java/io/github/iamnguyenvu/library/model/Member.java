package io.github.iamnguyenvu.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String id;
    private String name;
    private String email;
    private String membershipType; // Standard, Premium
    private LocalDate registeredDate;

    public Member(String id, String name, String email, String membershipType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.membershipType = membershipType;
        this.registeredDate = LocalDate.now();
    }
}
