# MVC & Observer pattern.
![hoi](https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/MVC-Process.svg/1200px-MVC-Process.svg.png)
### Inhoud
* Wat is MVC?
* Waarom MVC?
* Wat is Observer Pattern?
* Waarom Observer Pattern?
* MVC vs Observer Pattern.



### Wat is MVC?
* M staat voor Model. 
* V staat voor View.
* C staat voor Controller. <br><br>

De Model onthoudt alle data (denk aan variabelen) <br>
Je kan een model zien als een record uit de datatbase. <br><br>

Nu de V <br>
De View laat de informatie zien die in het Model staat opgeslagen.<br>
Denk bijvoorbeeld aan een HTML-bestand.<br><br>

De C staat voor Controller <br>
De Controller past de informatie aan die in het Model staat.<br>
Een voorbeeld hiervan is dat een PHP bestand die een formulier verwerkt.<br><br>

### Waarom MVC?

### Wat is het Observer Pattern?

Schreeuwen en luisteren binnen code.
![hoi](https://prismic-io.s3.amazonaws.com/zalando-jobsite/894dc02537d075480c53b7946c158c3ff69a286d_observer-pattern.png)

### Waarom Observer Pattern?

Voorbeeld Comminucatie tussen Model en View.<br>

```Java
ArrayList<Luisteraar> luisteraars = new Arraylist<>();

public void registreerMij(Luisteraar mij) {
   luisteraars.add(mij);
}

private void schreeuw() {
   for(Luisteraar luisteraar : luisteraars)
      luisteraar.luisterNaarMij();
}

private void aanpassing() { ... schreeuw(); }
```

### MVC vs Observer Pattern.

