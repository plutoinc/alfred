package network.pluto.alfred.models;

import network.pluto.alfred.validates.IdPwValidationGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class User implements Serializable {
    @NotNull(groups = {IdPwValidationGroup.class}) private Long id;
    @NotNull(groups = {IdPwValidationGroup.class}) private String password;
    private String walletAddress;

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

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
}
