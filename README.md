# Android Masaüstü Uygulaması Test Otomasyonu

Bu proje, Android masaüstü uygulaması, web uygulaması ve API'ler için kapsamlı bir test otomasyonu çerçevesi sağlar. Java dili kullanılarak Gauge framework, TestNG, Appium, Selenium ve REST Assured/Karate ile geliştirilmiştir.

## Proje Yapısı

```
android-desktop-automation/
├── src/
│   ├── main/
│   │   └── java/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           ├── appium/       # Android uygulaması testleri
│       │           ├── selenium/     # Web uygulaması testleri
│       │           ├── api/          # API testleri
│       │           └── gauge/        # Gauge adım implementasyonları
│       └── resources/
│           └── specs/                # Gauge test senaryoları
├── pom.xml                           # Maven yapılandırması
├── testng.xml                        # TestNG yapılandırması
└── README.md                         # Bu dosya
```

## Gereksinimler

- Java JDK 11 veya üzeri
- Maven 3.6.0 veya üzeri
- Appium 1.22.3 veya üzeri
- Android SDK
- Chrome/Firefox tarayıcı
- Gauge 1.4.3 veya üzeri

## Kurulum

1. Java JDK'yı yükleyin
2. Maven'ı yükleyin
3. Android SDK'yı yükleyin
4. Appium'u yükleyin:
   ```
   npm install -g appium@1.22.3
   ```
5. Gauge'u yükleyin:
   ```
   npm install -g @getgauge/cli
   gauge install java
   ```

## Testleri Çalıştırma

### Tüm Testleri Çalıştırma

```
mvn clean test
```

### Sadece Appium Testlerini Çalıştırma

```
mvn clean test -Dtest=AndroidAppTest
```

### Sadece Selenium Testlerini Çalıştırma

```
mvn clean test -Dtest=WebTest
```

### Sadece API Testlerini Çalıştırma

```
mvn clean test -Dtest=RestAssuredTest
```

### Gauge Testlerini Çalıştırma

```
gauge run specs/
```

## Test Türleri

### Android Uygulama Testleri

Android masaüstü uygulamasının temel işlevlerini test eder:
- Uygulama başlatma
- Giriş yapma
- Ana menü işlevleri
- Çıkış yapma

#### APK Dosyası Kullanımı

Bu test çerçevesi, `/Users/egehankomurcu/Downloads` dizinindeki APK dosyalarını kullanabilir. APK dosyasını kullanmak için:

1. Test etmek istediğiniz APK dosyasını `/Users/egehankomurcu/Downloads` dizinine kopyalayın
2. `src/test/java/com/example/gauge/AndroidAppSteps.java` ve `src/test/java/com/example/appium/AndroidAppTest.java` dosyalarındaki `APK_FILE_NAME` değişkenini APK dosyanızın adıyla güncelleyin:

```java
private static final String APK_FILE_NAME = "your-app.apk"; // APK dosyanızın adını buraya yazın
```

Eğer APK dosyası bulunamazsa, sistem otomatik olarak paket adı ve aktivite adını kullanarak uygulamayı başlatmaya çalışacaktır.

### Web Testleri

Web uygulamasının temel işlevlerini test eder:
- Web sitesi açma
- Kullanıcı girişi
- Ürün arama
- Oturum kapatma

### API Testleri

API'nin temel işlevlerini test eder:
- Kullanıcı listesi alma
- Yeni kullanıcı oluşturma
- Kullanıcı güncelleme
- Kullanıcı silme

## Katkıda Bulunma

1. Bu depoyu fork edin
2. Yeni bir özellik dalı oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add some amazing feature'`)
4. Dalınıza push edin (`git push origin feature/amazing-feature`)
5. Bir Pull Request açın

## Lisans

Bu proje MIT Lisansı altında lisanslanmıştır.
# sharp
