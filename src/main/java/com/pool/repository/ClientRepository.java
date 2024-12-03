package com.pool.repository;

import com.pool.model.Client;
import com.pool.repository.base.BaseRepository;

public interface ClientRepository extends BaseRepository<Client> {

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

}
