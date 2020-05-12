package com.example.lab.account.repository;

import com.example.lab.account.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{ 'mobiles' : {$all : [?0] }}")
    List<User> findByMobile(String[] mobiles);

    @Query(value = "{ 'emails' : {$all : [?0] }}")
    List<User> findByEmail(String[] emails);

    @Query(value = "{'$or':[{'username': '?0'},{'mobiles':{$all:['?0']}},{'emails': {$all: ['?0']}}]}")
    List<User> findByUsernameOrEmailOrMobile(String value);

    User findByIdAndSystemId(String id, String SystemId);

    User findByUsernameAndSystemId(String username, String SystemId);

    @Query(value = "{ 'mobiles' : {$all : [?0]}, 'systemId': ?1}")
    List<User> findByMobileAndSystemId(String[] mobiles, String systemId);

    @Query(value = "{ 'emails' : {$all : ?0}, 'systemId': ?1}")
    List<User> findByEmailAndSystemId(String[] emails, String systemId);

    @Query(value = "{'$or': [{'username': ?0}, {'mobiles': {$all: [?0]}}, {'emails': {$all: [?0]}}], 'systemId': ?1}")
    List<User> findByUsernameOrEmailOrMobileAndSystemId(String identity, String systemId);

    @Query(value = "{'$or': [{'username': ?0}, {'mobiles': {$all: [?0]}}, {'emails': {$all: [?0]}}], 'systemId': ?1}")
    User findOneByUsernameOrEmailOrMobileAndSystemId(String identity, String systemId);

    User findByThirdAccountsAndSystemId(String type, String userId, String systemId);
}
