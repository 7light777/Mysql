package com.yjt.basketball.dto;

public record AuthUser(
        Integer userId,
        String username,
        String displayName,
        String role
) {
    public String roleText() {
        return switch (role) {
            case "ADMIN" -> "管理员";
            case "SCOUT" -> "球探";
            case "VIEWER" -> "访客";
            default -> "用户";
        };
    }

    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }

    public boolean isScout() {
        return "SCOUT".equals(role);
    }

    public boolean canManageScoutNotes() {
        return isAdmin() || isScout();
    }
}
