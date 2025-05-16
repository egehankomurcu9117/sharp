# ERP Test Otomasyonu Projesi

Bu proje, ERP (Enterprise Resource Planning) sistemlerinin web arayüzlerini test etmek için Java ve Selenium WebDriver kullanılarak geliştirilmiş bir test otomasyonu çerçevesidir. Ayrıca Android masaüstü uygulaması, web uygulaması ve API'ler için de test desteği sağlar.

## Proje Yapısı

Proje, Page Object Model (POM) tasarım desenini kullanarak geliştirilmiştir. Bu desen, test kodunu sayfa nesneleri ve test sınıfları olarak ayırarak bakımı kolaylaştırır ve kod tekrarını önler.

```
src/
├── main/
│   └── java/
│       └── com/
│           ├── erp/
│           │   └── test/
│           │       ├── components/       # Özel bileşenler (tablolar, formlar vb.)
│           │       ├── core/             # Çekirdek bileşenler
│           │       │   ├── config/       # Yapılandırma yönetimi
│           │       │   ├── driver/       # WebDriver yönetimi
│           │       │   ├── page/         # Temel sayfa sınıfı
│           │       │   └── reporting/    # Raporlama
│           │       ├── data/             # Test verisi yönetimi
│           │       │   └── models/       # Veri modelleri
│           │       ├── pages/            # Sayfa nesneleri
│           │       │   ├── common/       # Ortak sayfalar
│           │       │   ├── finance/      # Finans modülü sayfaları
│           │       │   └── login/        # Giriş sayfaları
│           │       └── utils/            # Yardımcı sınıflar
│           └── example/
│               ├── appium/               # Android uygulaması testleri
│               ├── selenium/             # Web uygulaması testleri
│               ├── api/                  # API testleri
│               ├── gauge/                # Gauge adım implementasyonları
│               └── locators/             # JSON tabanlı lokator yönetimi
└── test/
    ├── java/
    │   └── com/
    │       ├── erp/
    │       │   └── test/
    │       │       ├── tests/            # Test sınıfları
    │       │       │   ├── finance/      # Finans modülü testleri
    │       │       │   └── login/        # Giriş testleri
    │       │       ├── steps/            # Cucumber adım tanımlamaları
    │       │       └── hooks/            # Test kancaları
    │       └── example/
    │           └── steps/                # Gauge adım tanımlamaları
    └── resources/
        ├── features/                     # Cucumber özellik dosyaları
        ├── specs/                        # Gauge test senaryoları
        ├── testdata/                     # Test verileri
        ├── locators/                     # JSON lokator dosyaları
        └── config/                       # Test yapılandırmaları
```

## Teknolojiler

Projede kullanılan temel teknolojiler:

- **Java 11+:** Ana programlama dili
- **Selenium WebDriver 4+:** Web otomasyon çerçevesi
- **Appium:** Mobil otomasyon çerçevesi
- **TestNG:** Test çerçevesi
- **Gauge/Cucumber:** BDD çerçeveleri
- **ExtentReports:** Raporlama
- **Log4j:** Loglama
- **REST Assured:** API testleri
- **Maven:** Bağımlılık yönetimi ve build aracı

## Gereksinimler

- Java JDK 11 veya üzeri
- Maven 3.6.0 veya üzeri
- Appium 1.22.3 veya üzeri (mobil testler için)
- Android SDK (mobil testler için)
- Chrome/Firefox/Edge tarayıcı
- Gauge 1.4.3 veya üzeri (BDD testleri için)

## Kurulum

1. Java JDK'yı yükleyin
2. Maven'ı yükleyin
3. Projeyi klonlayın: `git clone https://github.com/egehankomurcu9117/sharp.git`
4. Proje dizinine gidin: `cd sharp`
5. Bağımlılıkları yükleyin: `mvn clean install -DskipTests`

## Yapılandırma

Test yapılandırması `src/test/resources/config/config.properties` dosyasında bulunmaktadır. Bu dosyada aşağıdaki ayarları yapabilirsiniz:

- **browser:** Kullanılacak tarayıcı (chrome, firefox, edge, safari)
- **headless:** Tarayıcının görünmez modda çalışıp çalışmayacağı (true/false)
- **base.url:** Test edilecek ERP sisteminin URL'si
- **username:** Giriş için kullanılacak kullanıcı adı
- **password:** Giriş için kullanılacak şifre
- **company:** Giriş için kullanılacak şirket adı

## Testleri Çalıştırma

### Tüm Testleri Çalıştırma

```bash
mvn clean test
```

### ERP Web Testlerini Çalıştırma

```bash
mvn clean test -Dtest=com.erp.test.tests.*
```

### Android Testlerini Çalıştırma

```bash
mvn clean test -Dtest=AndroidAppTest
```

### API Testlerini Çalıştırma

```bash
mvn clean test -Dtest=RestAssuredTest
```

### Gauge Testlerini Çalıştırma

```bash
gauge run specs/
```

## Test Türleri

### ERP Web Testleri

ERP web uygulamasının temel işlevlerini test eder:
- Kullanıcı girişi
- Fatura işlemleri (oluşturma, düzenleme, silme)
- Raporlama
- Kullanıcı yönetimi

### Android Uygulama Testleri

Android masaüstü uygulamasının temel işlevlerini test eder:
- Uygulama başlatma
- Giriş yapma
- Ana menü işlevleri
- Çıkış yapma

#### APK Dosyası Kullanımı

Bu test çerçevesi, `/Users/egehankomurcu/Downloads` dizinindeki APK dosyalarını kullanabilir. APK dosyasını kullanmak için:

1. Test etmek istediğiniz APK dosyasını `/Users/egehankomurcu/Downloads` dizinine kopyalayın
2. `src/test/java/com/example/gauge/AndroidAppSteps.java` ve `src/test/java/com/example/appium/AndroidAppTest.java` dosyalarındaki `APK_FILE_NAME` değişkenini APK dosyanızın adıyla güncelleyin

### API Testleri

API'nin temel işlevlerini test eder:
- Kullanıcı listesi alma
- Yeni kullanıcı oluşturma
- Kullanıcı güncelleme
- Kullanıcı silme

## Özel Bileşenler

Proje, ERP sistemlerindeki karmaşık UI bileşenleri için özel işleyiciler içerir:

- **ERPDataTable:** Karmaşık veri tabloları için
- **ERPForm:** Formlar için
- **JSON Tabanlı Lokator Yönetimi:** Lokatorları JSON formatında saklama ve key ile erişim

## Raporlama

Test çalıştırıldıktan sonra, raporlar `target/reports` dizininde oluşturulur. HTML formatındaki raporları herhangi bir tarayıcıda açabilirsiniz.

## Katkıda Bulunma

1. Bu depoyu fork edin
2. Yeni bir özellik dalı oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add some amazing feature'`)
4. Dalınıza push edin (`git push origin feature/amazing-feature`)
5. Bir Pull Request açın

## Lisans

Bu proje MIT Lisansı altında lisanslanmıştır.
