# 🚀 JobPilot

An AI-powered job assistant built with Spring Boot that helps users manage their job search, optimize CVs, and track applications in one secure platform.

---

# 🇬🇧 English

## About

JobPilot is an AI-powered backend application designed to simplify the job hunting process.

The goal of the project is to provide a secure platform where users can:

- Create and manage professional CVs
- Organize education, experience and skills
- Track job applications
- Receive AI-powered CV improvements (planned)
- Match their profile with suitable job opportunities (planned)

The project is being developed incrementally following real-world backend development practices.

---

# Current Features (Version 1)

## Authentication & Security

- JWT Authentication
- Stateless Authentication
- BCrypt Password Encryption
- Spring Security
- Custom UserDetails
- Custom UserDetailsService
- AuthenticationManager
- JWT Authentication Filter
- Resource Ownership Protection (IDOR)
- Global Exception Handling

## User Management

- User Registration
- User Login
- View Profile
- Update Profile
- Delete Account

## CV Management

- Create CV
- View CV
- Update CV
- Delete CV

## Education Management

- Create
- Read
- Update
- Delete

## Experience Management

- Create
- Read
- Update
- Delete

## Skill Management

- Create
- Read
- Update
- Delete

## Job Applications

- Create Application
- View Applications
- Update Application Status
- Delete Application

## Jobs

- View Available Jobs

## Validation

- Bean Validation
- Request Validation
- Global Error Handling

---

# Technology Stack

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT (JSON Web Token)

### Database

- PostgreSQL

### Build Tool

- Maven

### Utilities

- Lombok
- Bean Validation

### Version Control

- Git
- GitHub

---

# Architecture

The project follows a layered architecture.

```
Controller
      ↓
Service
      ↓
Repository
      ↓
PostgreSQL
```

Additional layers:

- DTO Layer
- Mapper Layer
- Security Layer
- Exception Layer

---

# Security

JobPilot uses modern authentication and authorization techniques.

- JWT Authentication
- Stateless Sessions
- BCrypt Password Hashing
- AuthenticationManager
- Custom UserDetailsService
- JWT Filter
- Resource Ownership Validation (IDOR Protection)
- Secure Password Storage

---

# API Overview

## Authentication

```
POST /api/v1/users/register
POST /api/v1/auth/login
```

## Users

```
GET    /api/v1/users/{id}
PUT    /api/v1/users/{id}
DELETE /api/v1/users/{id}
```

## CV

```
POST   /api/v1/users/{userId}/cv
GET    /api/v1/users/{userId}/cv
PUT    /api/v1/cvs/{cvId}
DELETE /api/v1/cvs/{cvId}
```

## Education

```
POST
GET
PUT
DELETE
```

## Experience

```
POST
GET
PUT
DELETE
```

## Skills

```
POST
GET
PUT
DELETE
```

## Applications

```
POST
GET
PUT
DELETE
```

---

# Future Roadmap (Version 2)

- Multiple CV Support
- AI CV Review
- AI Cover Letter Generator
- Resume Version History
- Saved Jobs
- Job Recommendation Engine
- Smart Job Matching
- Email Notifications
- Dashboard Improvements
- Advanced Search & Filters

---

# Getting Started

Clone the repository

```bash
git clone https://github.com/bugarusan/job-pilot.git
```

Navigate to the project

```bash
cd job-pilot
```

Configure environment variables

```
DB_PASSWORD
JWT_SECRET
JWT_EXPIRATION
```

Run the project

```bash
./mvnw spring-boot:run
```

---

# Project Status

## ✅ Version 1 (Backend)

Completed

Includes:

- Authentication
- Authorization
- Security
- CRUD Operations
- Validation
- Exception Handling
- REST API

## 🚧 Version 2

In Progress

Upcoming AI-powered features and advanced job management.



---


# 🇹🇷 Türkçe

## Hakkında

JobPilot, kullanıcıların iş arama süreçlerini kolaylaştırmak amacıyla geliştirilen yapay zekâ destekli bir backend uygulamasıdır.

Kullanıcılar;

- Profesyonel CV oluşturabilir ve yönetebilir.
- Eğitim, iş deneyimi ve yetenek bilgilerini düzenleyebilir.
- İş başvurularını tek bir platformdan takip edebilir.
- Gelecekte yapay zekâ destekli CV analizi ve iş eşleştirme özelliklerinden faydalanabilir.

Proje, gerçek dünya backend geliştirme prensipleri izlenerek adım adım geliştirilmektedir.

---

## Mevcut Özellikler (Versiyon 1)

### Kimlik Doğrulama ve Güvenlik

- JWT Kimlik Doğrulama
- Stateless Authentication
- BCrypt ile Şifreleme
- Spring Security
- Custom UserDetails
- Custom UserDetailsService
- AuthenticationManager
- JWT Authentication Filter
- IDOR Koruması
- Global Exception Handling

### Kullanıcı Yönetimi

- Kullanıcı Kaydı
- Giriş Yapma
- Profil Görüntüleme
- Profil Güncelleme
- Hesap Silme

### CV Yönetimi

- CV Oluşturma
- CV Görüntüleme
- CV Güncelleme
- CV Silme

### Eğitim Yönetimi

- Oluştur
- Görüntüle
- Güncelle
- Sil

### Deneyim Yönetimi

- Oluştur
- Görüntüle
- Güncelle
- Sil

### Yetenek Yönetimi

- Oluştur
- Görüntüle
- Güncelle
- Sil

### İş Başvuruları

- Başvuru Oluşturma
- Başvuruları Görüntüleme
- Başvuru Durumu Güncelleme
- Başvuru Silme

### İş İlanları

- Mevcut İş İlanlarını Görüntüleme

---

## Teknoloji Yığını

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT

### Veritabanı

- PostgreSQL

### Build Tool

- Maven

### Diğer

- Lombok
- Bean Validation
- Git
- GitHub

---

## Mimari

Proje katmanlı mimari kullanılarak geliştirilmiştir.

```
Controller
      ↓
Service
      ↓
Repository
      ↓
PostgreSQL
```

Ek Katmanlar

- DTO Layer
- Mapper Layer
- Security Layer
- Exception Layer

---

## Güvenlik

- JWT Authentication
- Stateless Session
- BCrypt Password Hashing
- AuthenticationManager
- Custom UserDetailsService
- JWT Filter
- Resource Ownership Protection (IDOR)

---

## Yol Haritası (Versiyon 2)

- Çoklu CV Desteği
- AI Destekli CV Analizi
- AI Cover Letter Oluşturucu
- CV Sürüm Geçmişi
- Kaydedilen İş İlanları
- Akıllı İş Eşleştirme
- E-posta Bildirimleri
- Gelişmiş Dashboard
- Gelişmiş Arama ve Filtreleme

---

## Durum

### ✅ Versiyon 1 (Backend)

Tamamlandı

İçerik:

- Authentication
- Authorization
- Security
- CRUD İşlemleri
- Validation
- Exception Handling
- REST API

### 🚧 Versiyon 2

Geliştiriliyor.



---


# 🇯🇵 日本語

## 概要

JobPilot は、就職活動を効率化するために開発された AI 搭載バックエンドアプリケーションです。

ユーザーは以下のことができます。

- 履歴書（CV）の作成・管理
- 学歴・職歴・スキル情報の管理
- 求人応募の管理
- 将来的には AI による履歴書分析や求人マッチング機能を利用可能

本プロジェクトは、実際のバックエンド開発のベストプラクティスに従って段階的に開発されています。

---

## 現在の機能（バージョン1）

### 認証・セキュリティ

- JWT 認証
- Stateless Authentication
- BCrypt パスワード暗号化
- Spring Security
- Custom UserDetails
- Custom UserDetailsService
- AuthenticationManager
- JWT Authentication Filter
- IDOR 保護
- Global Exception Handling

### ユーザー管理

- ユーザー登録
- ログイン
- プロフィール閲覧
- プロフィール更新
- アカウント削除

### 履歴書（CV）管理

- 作成
- 取得
- 更新
- 削除

### 学歴管理

- 作成
- 取得
- 更新
- 削除

### 職歴管理

- 作成
- 取得
- 更新
- 削除

### スキル管理

- 作成
- 取得
- 更新
- 削除

### 応募管理

- 応募作成
- 応募一覧取得
- 応募状況更新
- 応募削除

### 求人情報

- 求人一覧取得

---

## 使用技術

### バックエンド

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT

### データベース

- PostgreSQL

### ビルドツール

- Maven

### その他

- Lombok
- Bean Validation
- Git
- GitHub

---

## アーキテクチャ

本プロジェクトはレイヤードアーキテクチャを採用しています。

```
Controller
      ↓
Service
      ↓
Repository
      ↓
PostgreSQL
```

追加レイヤー

- DTO Layer
- Mapper Layer
- Security Layer
- Exception Layer

---

## セキュリティ

- JWT Authentication
- Stateless Session
- BCrypt Password Hashing
- AuthenticationManager
- Custom UserDetailsService
- JWT Filter
- Resource Ownership Protection（IDOR）

---

## ロードマップ（バージョン2）

- 複数履歴書対応
- AI 履歴書分析
- AI カバーレター生成
- 履歴書バージョン管理
- 保存した求人
- AI 求人マッチング
- メール通知
- ダッシュボード改善
- 高度な検索・フィルター

---

## 開発状況

### ✅ バージョン1（バックエンド）

完成

内容

- Authentication
- Authorization
- Security
- CRUD Operations
- Validation
- Exception Handling
- REST API

### 🚧 バージョン2

開発中
