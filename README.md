میتونم متن راهنمای پروژه شما رو بدون ایموجی و به شکل استاندارد، مرتب و حرفه‌ای بازنویسی کنم تا برای داکیومنتیشن یا README قابل استفاده باشد. نسخه بازنویسی شده به صورت زیر است:

---

# Spring Boot CRUD Application with PostgreSQL

یک اپلیکیشن CRUD ساده با Spring Boot و PostgreSQL (بدون Docker)

## فهرست

* ویژگی‌ها
* پیش‌نیازها
* نصب و راه‌اندازی
* ساختار پروژه
* API Endpoints
* تست با curl
* مدل داده
* نکات مهم
* عیب‌یابی
* پشتیبانی

---

## ویژگی‌ها

* ایجاد، خواندن، به‌روزرسانی و حذف محصولات
* اتصال به دیتابیس PostgreSQL
* مدیریت خطاهای سراسری
* Validation ساده
* API های جستجو و فیلتر
* پشتیبانی CORS برای React

---

## پیش‌نیازها

قبل از اجرای پروژه، مطمئن شوید که موارد زیر نصب شده‌اند:

* Java 17 یا بالاتر
* Maven 3.6 یا بالاتر
* PostgreSQL 13 یا بالاتر

### نصب PostgreSQL

**Ubuntu/Debian:**

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

**macOS (با Homebrew):**

```bash
brew install postgresql
brew services start postgresql
```

**Windows:**

* دانلود و نصب از [postgresqltutorial.com](https://www.postgresqltutorial.com/)

---

## نصب و راه‌اندازی

### 1. کلون کردن پروژه

```bash
git clone <repository-url>
cd crudapp
```

یا فایل‌ها را در پوشه‌ای قرار دهید.

### 2. ایجاد دیتابیس

وارد کنسول PostgreSQL شوید و دیتابیس بسازید:

```sql
sudo -u postgres psql
CREATE DATABASE crud_db;
CREATE USER crud_user WITH ENCRYPTED PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE crud_db TO crud_user;
```

### 3. تنظیم اتصال دیتابیس

فایل `src/main/resources/application.properties` را ویرایش کنید:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/crud_db
spring.datasource.username=postgres
spring.datasource.password=your_password
# یا کاربر سفارشی
# spring.datasource.username=crud_user
# spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```

### 4. اجرای پروژه

**با Maven:**

```bash
mvn spring-boot:run
```

**با JAR file:**

```bash
mvn clean package
java -jar target/crudapp-0.0.1-SNAPSHOT.jar
```

**با IDE:**
کلاس `CrudappApplication.java` را اجرا کنید.

### 5. تایید اجرا

* Backend روی `http://localhost:8080` در دسترس است.
* API پایه: `http://localhost:8080/api/products`

---

## ساختار پروژه

```
crudapp/
├── pom.xml
├── src/
│   └── main/
│       ├── java/com/example/crudapp/
│       │   ├── CrudappApplication.java
│       │   ├── controller/ProductController.java
│       │   ├── service/ProductService.java
│       │   ├── repository/ProductRepository.java
│       │   ├── model/Product.java
│       │   └── exception/GlobalExceptionHandler.java
│       └── resources/application.properties
```

---

## API Endpoints

**پایه:** `/api/products`

| Method | Endpoint           | Description         |
| ------ | ------------------ | ------------------- |
| POST   | /api/products      | ایجاد محصول جدید    |
| GET    | /api/products      | دریافت تمام محصولات |
| GET    | /api/products/{id} | دریافت محصول با ID  |
| PUT    | /api/products/{id} | به‌روزرسانی محصول   |
| DELETE | /api/products/{id} | حذف محصول           |

**اضافی:**

| Method | Endpoint                                           | Description         |
| ------ | -------------------------------------------------- | ------------------- |
| GET    | /api/products/active                               | دریافت محصولات فعال |
| GET    | /api/products/search?name=keyword                  | جستجو با نام        |
| GET    | /api/products/price-range?minPrice=10&maxPrice=100 | فیلتر قیمت          |
| GET    | /api/products/health                               | بررسی سلامت سرویس   |

---

## تست با curl

**ایجاد محصول جدید:**

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "لپ تاپ دل",
    "price": 1500.00,
    "description": "لپ تاپ قدرتمند برای کار",
    "stockQuantity": 10,
    "isActive": true
  }'
```

**دریافت تمام محصولات:**

```bash
curl -X GET http://localhost:8080/api/products
```

**دریافت محصول خاص:**

```bash
curl -X GET http://localhost:8080/api/products/1
```

**به‌روزرسانی محصول:**

```bash
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "لپ تاپ دل - آپدیت شده",
    "price": 1600.00,
    "description": "لپ تاپ قدرتمند و جدید",
    "stockQuantity": 5,
    "isActive": true
  }'
```

**حذف محصول:**

```bash
curl -X DELETE http://localhost:8080/api/products/1
```

**جستجو با نام:**

```bash
curl -X GET "http://localhost:8080/api/products/search?name=لپ"
```

**فیلتر قیمت:**

```bash
curl -X GET "http://localhost:8080/api/products/price-range?minPrice=500&maxPrice=2000"
```

**محصولات فعال:**

```bash
curl -X GET http://localhost:8080/api/products/active
```

**بررسی سلامت سرویس:**

```bash
curl -X GET http://localhost:8080/api/products/health
```

---

## مدل داده

جدول `products` در دیتابیس:

| فیلد          | نوع           | توضیحات            |
| ------------- | ------------- | ------------------ |
| id            | BigInt        | کلید اصلی (خودکار) |
| name          | Varchar       | نام محصول (ضروری)  |
| price         | Decimal(10,2) | قیمت محصول         |
| description   | Text          | توضیحات            |
| stockQuantity | Integer       | تعداد موجودی       |
| isActive      | Boolean       | وضعیت فعال بودن    |

---

## نکات مهم

* PostgreSQL باید در حال اجرا باشد قبل از راه‌اندازی اپلیکیشن.
* دیتابیس باید از قبل ایجاد شده باشد (Hibernate جداول را ایجاد می‌کند).
* تنظیمات اتصال را بررسی کنید در فایل `application.properties`.
* پورت 8080 باید آزاد باشد برای اجرای سرویس.

---

## عیب‌یابی

**خطای اتصال به دیتابیس:**

```bash
sudo systemctl status postgresql
sudo systemctl restart postgresql
```

**خطای پورت در حال استفاده:**

```bash
lsof -i :8080
kill -9 <PID>
```

**مشکل Maven:**

```bash
mvn clean
mvn dependency:resolve
```

---

## پشتیبانی

در صورت مشکل، پیام خطا و مراحل تکرار شده را ارائه دهید.

---

