package com.life.lifestyle.Data;

public class StaticData {
    private static boolean isLogin;
    private static User user;
    StaticData()
    {
        isLogin=false;
        user = new User();
    }
    public static void setUsers(User users) {
        StaticData.user = users;
    }

    public static User getUser() {
        return user;
    }

    public static void setIsLogin(boolean isLogin) {
        StaticData.isLogin = isLogin;
    }

    public static boolean isLogin() {
        return isLogin;
    }
}
