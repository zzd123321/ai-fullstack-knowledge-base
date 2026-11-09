/**
 * 提供 Demo 项目的公共 API 接口。
 * <p>
 * 此包包含所有对外暴露的服务契约，外部模块只需依赖此包即可调用。
 * 使用此包时，请确保传入的参数符合非空约束。
 * </p>
 *
 * @author 你的名字
 * @since 1.0.0
 */
// 1. 包级注解（放在 package 声明之前）
@Deprecated  // 示例：标记整个包为已弃用（若需要）
package com.example.demo.api;

// 2. 导入放在 package 声明之后
import java.lang.Deprecated;