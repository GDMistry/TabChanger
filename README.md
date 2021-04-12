# TabChanger
A library to change tabs, select one item at a time

Add this in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 
 
Add the dependency:

	dependencies {
	        implementation 'com.github.GDMistry:TabChanger:{version}'
	}
  
  
Demo implementation:

        binding.tabChanger.setTabItemNames(ArrayList<String>().apply {
            add("Cold")
            add("Hot")
            add("Dry")
        })
        binding.tabChanger.setTabItemImages(ArrayList<Int>().apply {
            add(R.drawable.ic_arrow_back_black)
            add(R.drawable.ic_arrow_back_black)
            add(R.drawable.ic_arrow_back_black)
        })
        binding.tabChanger.setImageAsResource = true
        binding.tabChanger.scaleType = ImageView.ScaleType.CENTER_INSIDE
        binding.tabChanger.firstTabToSelectIndex = 3
        
        //Call at the end
        binding.tabChanger.setup()

Demo image :

![image](https://user-images.githubusercontent.com/66417665/114370926-a0825980-9b9d-11eb-80fa-9561bf23e396.png)
