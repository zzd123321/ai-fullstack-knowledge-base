-- 1. 插入基础权限
INSERT INTO permissions (resource, action, description) VALUES
('article', 'create', '创建文章'),
('article', 'read', '阅读文章'),
('article', 'update', '更新文章'),
('article', 'delete', '删除文章'),
('user', 'read', '查看用户信息'),
('user', 'create', '创建用户'),
('user', 'update', '更新用户信息'),
('user', 'delete', '删除用户');

-- 2. 插入基础角色（支持继承）
INSERT INTO roles (name, description, parent_role_id, is_system) VALUES
('viewer', '只读访客', NULL, TRUE),
('editor', '内容编辑', 1, TRUE),      -- 继承 viewer
('admin', '系统管理员', 2, TRUE);     -- 继承 editor

-- 3. 为角色分配权限
-- viewer: 只能阅读
INSERT INTO role_permissions (role_id, permission_id)
SELECT 1, id FROM permissions WHERE resource = 'article' AND action = 'read';

-- editor: 阅读 + 创建 + 更新（自己的）
INSERT INTO role_permissions (role_id, permission_id)
SELECT 2, id FROM permissions 
WHERE (resource = 'article' AND action IN ('read', 'create', 'update'))
   OR (resource = 'user' AND action = 'read');

-- admin: 全部权限
INSERT INTO role_permissions (role_id, permission_id)
SELECT 3, id FROM permissions;

-- 4. 插入测试用户（密码为：123456，使用BCrypt哈希）
INSERT INTO users (username, email, password_hash, is_active) VALUES
('admin', 'admin@example.com', '$2a$10$NkM...', TRUE),
('editor', 'editor@example.com', '$2a$10$NkM...', TRUE),
('viewer', 'viewer@example.com', '$2a$10$NkM...', TRUE);

-- 5. 为用户分配角色
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 3),  -- admin → admin角色
(2, 2),  -- editor → editor角色
(3, 1);  -- viewer → viewer角色