package network.pluto.alfred.models;

import network.pluto.alfred.validates.IdPwValidationGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "PLUTO_USER")
public class User implements Serializable {
    @Id
    @GeneratedValue
    @NotNull(groups = {IdPwValidationGroup.class})
    private Long id;

    @NotNull(groups = {IdPwValidationGroup.class})
    private String password;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Wallet wallet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
