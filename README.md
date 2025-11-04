🚀 Spring Boot CRUD Application with PostgreSQL
یک اپلیکیشن CRUD ساده با Spring Boot و PostgreSQL (بدون Docker)

📋 فهرست
ویژگی‌ها
پیش‌نیازها
نصب و راه‌اندازی
ساختار پروژه
API Endpoints
تست با curl
✨ ویژگی‌ها
✅ ایجاد، خواندن، به‌روزرسانی و حذف محصولات
✅ اتصال به دیتابیس PostgreSQL
✅ مدیریت خطاهای سراسری
✅ Validation ساده
✅ API های جستجو و فیلتر
✅ Cross-Origin Resource Sharing (CORS) برای React
🔧 پیش‌نیازها
قبل از اجرای پروژه، مطمئن شوید که موارد زیر نصب شده‌اند:

Java 17 یا بالاتر
Maven 3.6 یا بالاتر
PostgreSQL 13 یا بالاتر
نصب PostgreSQL
در Ubuntu/Debian:
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
در macOS (با Homebrew):
brew install postgresql
brew services start postgresql
در Windows:
دانلود و نصب از postgresqltutorial.com

🚀 نصب و راه‌اندازی
1. کلون کردن پروژه
# اگر پروژه در Git است
git clone <repository-url>
cd crudapp

# یا فایل‌ها را در پوشه‌ای قرار دهید
2. ایجاد دیتابیس
وارد کنسول PostgreSQL شوید و دیتابیس بسازید:

# ورود به کنسول PostgreSQL
sudo -u postgres psql

# ایجاد دیتابیس
CREATE DATABASE crud_db;

# ایجاد کاربر (اختیاری)
CREATE USER crud_user WITH ENCRYPTED PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE crud_db TO crud_user;
3. تنظیم اتصال دیتابیس
فایل src/main/resources/application.properties را ویرایش کنید:

# تنظیمات اتصال به دیتابیس PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/crud_db
spring.datasource.username=postgres
spring.datasource.password=your_password

# اگر از کاربر سفارشی استفاده می‌کنید:
# spring.datasource.username=crud_user
# spring.datasource.password=your_password
4. اجرای پروژه
با Maven:
# دانلود dependencies و اجرا
mvn spring-boot:run
با JAR file:
# ساخت JAR
mvn clean package

# اجرای JAR
java -jar target/crudapp-0.0.1-SNAPSHOT.jar
با IDE:
کلاس CrudappApplication.java را در IDE خود اجرا کنید.

5. تایید اجرا
در کنسول پیام زیر را خواهید دید:

🚀 CRUD Application started successfully!
📡 Server running on: http://localhost:8080
📚 API endpoints available at: http://localhost:8080/api/products
📁 ساختار پروژه
crudapp/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── crudapp/
│       │               ├── CrudappApplication.java
│       │               ├── controller/
│       │               │   └── ProductController.java
│       │               ├── service/
│       │               │   └── ProductService.java
│       │               ├── repository/
│       │               │   └── ProductRepository.java
│       │               ├── model/
│       │               │   └── Product.java
│       │               └── exception/
│       │                   └── GlobalExceptionHandler.java
│       └── resources/
│           └── application.properties
🌐 API Endpoints
پایه: /api/products
Method	Endpoint	Description
POST	/api/products	ایجاد محصول جدید
GET	/api/products	دریافت تمام محصولات
GET	/api/products/{id}	دریافت محصول با ID
PUT	/api/products/{id}	به‌روزرسانی محصول
DELETE	/api/products/{id}	حذف محصول
اضافی:
Method	Endpoint	Description
GET	/api/products/active	محصولات فعال
GET	/api/products/search?name=keyword	جستجو با نام
GET	/api/products/price-range?minPrice=10&maxPrice=100	فیلتر قیمت
GET	/api/products/health	بررسی سلامت سرویس
🧪 تست با curl
1. ایجاد محصول جدید (CREATE)
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "لپ تاپ دل",
    "price": 1500.00,
    "description": "لپ تاپ قدرتمند برای کار",
    "stockQuantity": 10,
    "isActive": true
  }'
2. دریافت تمام محصولات (READ ALL)
curl -X GET http://localhost:8080/api/products
3. دریافت محصول خاص (READ ONE)
curl -X GET http://localhost:8080/api/products/1
4. به‌روزرسانی محصول (UPDATE)
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "لپ تاپ دل - آپدیت شده",
    "price": 1600.00,
    "description": "لپ تاپ قدرتمند و جدید",
    "stockQuantity": 5,
    "isActive": true
  }'
5. حذف محصول (DELETE)
curl -X DELETE http://localhost:8080/api/products/1
6. جستجو با نام
curl -X GET "http://localhost:8080/api/products/search?name=لپ"
7. فیلتر قیمت
curl -X GET "http://localhost:8080/api/products/price-range?minPrice=500&maxPrice=2000"
8. محصولات فعال
curl -X GET http://localhost:8080/api/products/active
9. بررسی سلامت
curl -X GET http://localhost:8080/api/products/health
📦 مدل داده
جدول products در دیتابیس:

فیلد	نوع	توضیحات
id	BigInt	کلید اصلی (خودکار)
name	Varchar	نام محصول (ضروری)
price	Decimal(10,2)	قیمت محصول
description	Text	توضیحات
stockQuantity	Integer	تعداد موجودی
isActive	Boolean	وضعیت فعال بودن
⚠️ نکات مهم
PostgreSQL باید در حال اجرا باشد قبل از راه‌اندازی اپلیکیشن
دیتابیس باید از قبل ایجاد شده باشد (Hibernate جداول را ایجاد می‌کند)
تنظیمات اتصال را بررسی کنید در فایل application.properties
پورت 8080 باید آزاد باشد برای اجرای سرویس
🐛 عیب‌یابی
خطای اتصال به دیتابیس:
# بررسی وضعیت PostgreSQL
sudo systemctl status postgresql

# راه‌اندازی مجدد
sudo systemctl restart postgresql
خطای پورت در حال استفاده:
# پیدا کردن پروسه با پورت 8080
lsof -i :8080

# توقف پروسه
kill -9 <PID>
مشکل Maven:
# پاک کردن کش Maven
mvn clean

# دانلود مجدد dependencies
mvn dependency:resolve
📞 پشتیبانی
اگر مشکلی داشتید، لطفاً پیام خطا و مراحل تکرار شده را ارائه دهید.

در حال حاضر Front ui کار نمیکند ...در جریان باشید 
