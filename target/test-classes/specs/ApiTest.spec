# API Testi

Bu test senaryosu, API'nin temel işlevlerini test eder.

## Kullanıcı Listesi Alma
* Kullanıcı listesi API'sine GET isteği gönder
* Yanıt kodunun 200 olduğunu doğrula
* Yanıtın geçerli bir JSON olduğunu doğrula
* Kullanıcı listesinin boş olmadığını doğrula

## Yeni Kullanıcı Oluşturma
* Yeni kullanıcı verilerini hazırla
* Kullanıcı oluşturma API'sine POST isteği gönder
* Yanıt kodunun 201 olduğunu doğrula
* Oluşturulan kullanıcı ID'sini al
* Kullanıcının başarıyla oluşturulduğunu doğrula

## Kullanıcı Güncelleme
* Kullanıcı güncelleme verilerini hazırla
* Kullanıcı güncelleme API'sine PUT isteği gönder
* Yanıt kodunun 200 olduğunu doğrula
* Kullanıcının başarıyla güncellendiğini doğrula

## Kullanıcı Silme
* Kullanıcı silme API'sine DELETE isteği gönder
* Yanıt kodunun 204 olduğunu doğrula
* Kullanıcının başarıyla silindiğini doğrula
