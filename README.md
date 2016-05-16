# Navi
<img src = "http://vignette3.wikia.nocookie.net/zelda/images/2/2c/Navi_Artwork.png/revision/latest?cb=20090319134157"/><br/>
Rx-based Event bus for Android. <br/>
<h3><b>Hey, Listen! </b></h3>
<h4>Usage:</h4>

```java
class Kokiri extends AppCompatActivity{
  Navi navi = new Navi();
  ....
  
  //Registration
  @Override
  public void onResume(){
    navi.call(this);
    
  }
  
  //Unregistration
  @Override
  public void onPause(){
    navi.swat(this);
  }
  ...
  //Event posting
  navi.hey(new Annoyance());
  
  @Listen
  public void onAnnoyed(Annoyance event){
    ...
  }
  

}
```
