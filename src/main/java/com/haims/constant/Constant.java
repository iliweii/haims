package com.haims.constant;

public class Constant {

	/* session状态 */
	public static String SESSION_USER = "userid";
	public static String SESSION_USER_ROLE = "roleid";
	public static String SYS_ADMIN_ID = "1000000000000001"; // 超级管理员用户ID

	/* 用户状态 */
	public static int USER_STATE_USE = 0; // 可用用户信息
	public static int USER_STATE_UNUSE = 1; // 不可用用户信息

	/* 角色分类 */
	public static int ROLE_TYPE_1 = 1; // 管理员
	public static int ROLE_TYPE_2 = 2; // 业务人员
	public static int ROLE_TYPE_3 = 3; // 库存管理人员

	/* 角色状态 */
	public static int ROLE_STATE_USE = 0; // 角色状态可用
	public static int ROLE_STATE_UNUSE = 1; // 角色状态不可用

	/* 分类状态 */
	public static int TYPE_STATE_USE = 0; // 分类可用
	public static int TYPE_STATE_UNUSE = 1; // 分类不可用

	/* 材料状态 */
	public static int STUFF_STATE_0 = 0; // 材料充足
	public static int STUFF_STATE_1 = 1; // 材料不足
	public static int STUFF_STATE_2 = 2; // 删除

	/* 订单状态 */
	public static int ORDER_STATE_1 = 1; // 入库订单
	public static int ORDER_STATE_2 = 2; // 销售订单
	public static int ORDER_STATE_3 = 3; // 退货订单

	/* 订单删除状态 */
	public static int ORDER_DELETE_0 = 0; // 未删除
	public static int ORDER_DELETE_1 = 1; // 订单管理员删除
	public static int ORDER_DELETE_2 = 2; // 超级管理员删除订单
}
