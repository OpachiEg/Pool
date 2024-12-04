package com.pool.repository;

import com.pool.model.Client;
import com.pool.repository.base.BaseRepository;

public interface ClientRepository extends BaseRepository<Client> {

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email,Long id);

    boolean existsByPhoneAndIdNot(String phone,Long id);

}
