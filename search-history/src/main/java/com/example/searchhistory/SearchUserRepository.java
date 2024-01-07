package com.example.searchhistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchUserRepository extends JpaRepository<SearchUser, Long> {

    public SearchUser findSearchUserByUser(String name);
}
