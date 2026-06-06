package com.yjt.basketball.mapper;

import com.yjt.basketball.dto.row.AuthUserRow;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {

    void createUserAccountTable();

    int insertDefaultUsers();

    AuthUserRow selectByUsernameAndPassword(@Param("username") String username,
                                            @Param("password") String password);
}
