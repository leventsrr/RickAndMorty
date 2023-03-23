# RICK AND MORTY (Kotlin-Android Uygulama)
## Kullanılan Yapılar
* MVVM Mimarisi
* Dagger Hilt
* Shared Preferences
* Glide
* Navigation
* Live Data
* Retrofit
* Okhttp
* Coroutines
* Data Binding

## Ekranlar
1- Splash Screen (Karşılama Ekranı)
* Uygulama açıldığında kullancıyı karşılayan ekrandır. Uygulama telefonda ilk kez açılmışsa karşılamayazısı Welcome!, Daha önceden açılmışsa Hello! olmakatadır. Bu işlev Shared Preferences yapısı kullanılarak oluşturulmuştur.Örnek kodlar:

```
override fun writeIsLoginInfo(key: String, value: Boolean) {
    val editor : SharedPreferences.Editor = sharedPreferences.edit()
    editor.putBoolean(key,value)
    editor.apply()
}

override fun readIsLoginInfo(key: String): Boolean {
    return sharedPreferences.getBoolean(key,false)
}
```
2- Home Screen (Anaekran)
* Anaekranda kullanıcıyı önce diziye ait konumların ilk 20 tanesi karşılamaktadır.Kullanıcı hangi konumun karakterlerini görmek istiyorsa o konuma tıklar.Böylelikle konuma ait karakterler kullanıcıya listelener.Bu işlev uygulamada Retfofit ve Live Data yapıları ile gerçekleştirilmiştir. Karakterler ve konumlar RecyclerView kullanılarak listelenmiştir.Karakterlerin görselleri Glide kullanılarak ekrana getirilmiştir.

a) Retrofit İle API İstek Örneği:
```
@GET("location/{locationId}")
    suspend fun getALocationById(@Path("locationId") locationId: Int): Response<LocationByIdModel>
```

b) Live Data İle API Cevabının Listelenmesi Örneği:
```
private val _location  : MutableLiveData<Resource<LocationByIdModel>?> = MutableLiveData(null)
val location : LiveData<Resource<LocationByIdModel>?> get() = _location

suspend fun getALocationById(locationId:Int) = viewModelScope.launch {
    _location.value = Resource.Loading
    val result = apiRepository.getALocationById(locationId)
    _location.value = result
}

private fun subscribeLocationObserve() {
    apiViewModel.location.observe(viewLifecycleOwner) {
        when (it) {
            is Resource.Failure -> {
                Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG).show()
            }
            is Resource.Loading -> { }
            is Resource.Success -> {
                for (residentLink in it.result.residents) {
                    val characterId = residentLink.split("/").last().toInt()
                    characterIdArray.add(characterId)
                }

                runBlocking {
                    apiViewModel.getMultipleCharacterById(characterIdArray)
                    characterIdArray.clear()
                }

            }
            else -> {
                Log.e("control", "location observe function in else HomeFragment")
            }
        }
    }
}
```
c) RecyclerView Kullanım Örneği:
```
private fun setupLocationAdapter() {
    binding.rwLocationList.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    locationAdapter = LocationAdapter()
    binding.rwLocationList.adapter = locationAdapter
    locationAdapterOnClickListener()
    locationAdapterScrollListener(binding.rwLocationList)
}
```
3- Detail Page (Detay Ekranı)
* Bu ekranda karakter listesinden seçilen karakterin detaylı bilgileri gösterilir. Bilgiler Anaekrandan bu ekrana Custom Parcelable yapısı kullanılarak getirilir. Önceki ekrandan gelen veriler gerekli yerlere bağlanır.

a) findNavController() kullanılarak anaekrandan veri taşıma örneği:
```
characterAdapter.moveDetailPage {
    val action = HomeFragmentDirectionsactionHomeFragmentToCharacterDetailFragment(it)
    findNavController().navigate(action)
}
```
b) Anaekrandan gelen modeli detay ekranında elde etme:
```
private fun handleArguments() {
    arguments?.let {
        val argsModel = CharacterDetailFragmentArgs.fromBundle(it).characterDetailModel
        characterDetailModel = argsModel
    }
}
```
---
* Uygulama içinde Dependency Injection için Dagger Hilt kullanılmıştır.
* Ekran arası yönlendirme için Navigation yapısı kullanılmıştır.
* XML dosyalarında bulunan arayüzlerin Kotlin dosyalarıyla bağlantı kurabilmesi için Data Binding kullanılmıştır
---


https://user-images.githubusercontent.com/63983517/227302035-02beea2d-9135-4b80-bcd0-91fc369b17bd.mp4




