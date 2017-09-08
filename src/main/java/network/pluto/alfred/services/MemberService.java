package network.pluto.alfred.services;

import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Wallet;

public interface MemberService {
    public Member getMemberByIdAndPassword(Long id, String password);
    public void setWallet(Member member, Wallet wallet);
}
