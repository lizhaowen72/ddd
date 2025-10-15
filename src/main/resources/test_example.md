##  运行和测试

### 启动应用：
```bash
mvn spring-boot:run
```

### 测试API：

1. **创建订单**：
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "customer-456",
    "street": "456 Oak Ave",
    "city": "Boston", 
    "postalCode": "02101"
  }'
```

2. **添加商品**：
```bash
curl -X POST http://localhost:8080/api/orders/ORDER-{your-order-id}/items \
  -H "Content-Type: application/json" \
  -d '{
    "productId": "prod-3",
    "productName": "Keyboard",
    "quantity": 1,
    "unitPrice": 79.99
  }'
```
curl -X POST http://localhost:8080/api/orders/ORDER-21841d40-0a8e-45c4-99cd-9d5f2b4fffe7/items \
-H "Content-Type: application/json" \
-d '{
"productId": "prod-3",
"productName": "Keyboard",
"quantity": 1,
"unitPrice": 79.99
}'




3. **确认订单**：
```bash
curl -X POST http://localhost:8080/api/orders/ORDER-{your-order-id}/confirm
```

curl -X POST http://localhost:8080/api/orders/ORDER-21841d40-0a8e-45c4-99cd-9d5f2b4fffe7/confirm

4. **查看订单**：
```bash
curl http://localhost:8080/api/orders/ORDER-{your-order-id}
```

curl http://localhost:8080/api/orders/ORDER-21841d40-0a8e-45c4-99cd-9d5f2b4fffe7

### 访问H2控制台：
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- 用户名: `sa`
- 密码: (空)

## 关键设计要点：

1. **聚合边界清晰**：Order聚合只包含Order和OrderLine，不包含支付、物流等
2. **不变条件保护**：业务规则在聚合内强制执行
3. **值对象使用**：Money、Address等作为值对象确保数据一致性
4. **懒加载**：OrderLine使用LAZY加载防止N+1查询
5. **事务边界**：每个事务只修改一个聚合