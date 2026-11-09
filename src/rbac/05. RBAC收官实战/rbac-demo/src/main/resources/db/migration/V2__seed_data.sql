-- ============================================================
-- V2: 初始化种子数据
-- ============================================================

-- ===================== 角色 =====================
INSERT INTO roles (name, description, is_system) VALUES
    ('ADMIN',   '系统管理员，拥有全部权限', TRUE),
    ('EDITOR',  '编辑，可读/写/删文章',     TRUE),
    ('VIEWER',  '访客，仅可读文章',         TRUE);

-- ===================== 权限 =====================
INSERT INTO permissions (resource, action, description) VALUES
    ('article', 'read',   '查看文章'),
    ('article', 'create', '创建文章'),
    ('article', 'update', '更新文章'),
    ('article', 'delete', '删除文章'),
    ('user',    'read',   '查看用户列表');

-- ===================== 角色-权限映射 =====================
-- ADMIN 拥有全部权限
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5);

-- EDITOR 拥有 article 的 read/create/update/delete
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (2, 1), (2, 2), (2, 3);

-- VIEWER 仅拥有 article:read
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (3, 1);

-- ===================== 用户 =====================
-- 密码均为 admin123，BCrypt 哈希
INSERT INTO users (username, email, password_hash, is_active) VALUES
    ('admin',  'admin@example.com',  '$2a$12$OUbpMVw32ef9bpIpCihdCucDRjDz83iSTBzKrTgbS1d5obLOlhRKu',  TRUE),
    ('editor', 'editor@example.com', '$2a$12$OUbpMVw32ef9bpIpCihdCucDRjDz83iSTBzKrTgbS1d5obLOlhRKu',  TRUE),
    ('viewer', 'viewer@example.com', '$2a$12$OUbpMVw32ef9bpIpCihdCucDRjDz83iSTBzKrTgbS1d5obLOlhRKu',  TRUE);

-- ===================== 用户-角色映射 =====================
INSERT INTO user_roles (user_id, role_id) VALUES
    (1, 1),  -- admin  -> ADMIN
    (2, 2),  -- editor -> EDITOR
    (3, 3);  -- viewer -> VIEWER

-- ===================== 测试文章 =====================
INSERT INTO articles (title, content, author_id) VALUES
    ('欢迎使用 RBAC 系统', '这是一个基于 Spring Boot + JWT + RBAC 的权限管理系统示例。', 1),
    ('第二篇文章', '由编辑创建的第二篇测试文章。', 2);
