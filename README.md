<div align="center">

  <h1> 🔐 Spring Identity Core </h1>
  <p><b>מערכת ניהול זהויות והרשאות מתקדמת ב-Spring Boot</b></p>

  <a href="https://hub.docker.com/r/yonatanfarhi20/spring-identity-core">
    <img src="https://img.shields.io/badge/Docker-Image-blue?style=for-the-badge&logo=docker" alt="Docker Badge">
  </a>
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk" alt="Java Badge">
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot Badge">
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="Postgres Badge">

  <hr />
</div>

## 📝 אודות הפרויקט
מערכת Backend המדגימה ניהול משתמשים מלא עם הפרדה בין תפקידי **מנהל (Admin)** ו**משתמש רגיל (User)**. הפרויקט נבנה בדגש על ארכיטקטורה נקייה, אבטחה ופריסה מהירה באמצעות Docker.

### ✨ תכונות עיקריות
* **אימות משתמשים (Authentication):** מערכת Login מאובטחת.
* **ניהול הרשאות (Role-Based Access Control):** הגבלת גישה לנתיבים רגישים למנהלים בלבד.
* **Docker Ready:** קונטיינריזציה מלאה של האפליקציה ובסיס הנתונים.
* **PostgreSQL Persistence:** שמירת נתונים יציבה ומקצועית.

---

## 🛠 טכנולוגיות
<table align="center">
  <tr>
    <td align="center"><b>Backend</b></td>
    <td align="center"><b>Database</b></td>
    <td align="center"><b>DevOps</b></td>
  </tr>
  <tr>
    <td>Java 17, Spring Boot</td>
    <td>PostgreSQL 15</td>
    <td>Docker & Docker Compose</td>
  </tr>
</table>

---

## 🚀 איך להתחיל? (שימוש ב-Swagger)

כדי לבחון את ה-API, עקבו אחר השלבים הבאים לאחר הרצת הפרויקט:

1. **הרשמה (Register):** בצעו קריאת `POST` לנתיב ה-Registration עם פרטי המשתמש שלכם.
2. **התחברות (Login):** בצעו קריאת `POST` לנתיב ה-Login. אם הפרטים נכונים, תקבלו בתגובה **JWT Token**.
3. **אימות (Authorization):**
   * העתיקו את הטוקן שקיבלתם.
   * לחצו על כפתור ה-**Authorize** (סמל המנעול) בראש דף ה-Swagger.
   * הדביקו את הטוקן בתיבה שנפתחה ולחצו על **Authorize**.
5. **שימוש בקריאות:** עכשיו תוכלו לבצע קריאות לנתיבים המוגנים (כמו נתיבי ה-Admin) בהתאם להרשאות שלכם.

---
