Uçuş Rezervasyon Sistemi
Uçuş biletleri için gereken temel işlemler yapılmıştır. Aşağıdaki REST servisleri ile bilet rezervasyon sistemi oluşturulmuştur.

Uçuş işlemleri: Uçuş ekleme, silme ve güncelleme işlemleri yapılmıştır.
Koltuk işlemleri: Mevcut uçuşlar için koltuk ekleme, silme ve güncelleme hizmetleri oluşturulmuştur.
Uçuş/Koltuk listeleme servisi: Uçuş adı, açıklaması, mevcut koltuklar ve fiyatı dönen bir hizmet sağlanmıştır.
Ödeme hizmeti: Kullanıcıların seçilen koltuğu satın alabilecekleri bir ödeme hizmeti eklenmiştir.
Koltuk satışı, birden fazla yolcuya yapılmamış, aynı koltuk iki farklı kişiye satılmamıştır.
Aynı anda iki yolcu aynı koltuğa ödeme yapmaya çalıştığında, ilk başarılı ödeme koltuğu satın almış, ikinci ödeme başarısız olmuştur ve bu durum için uygun bir hata mesajı döndürülmüştür. Bu senaryo için IT testi yapılmıştır.
Ön yüz geliştirmesi yapılmamıştır.
Uygulanan servislerin test kapsamı %80'in üzerine çıkarılmıştır. Hem entegrasyon hem de birim testleri yapılmıştır.
Üretim seviyesinde bir çözüm oluşturulmuştur.
