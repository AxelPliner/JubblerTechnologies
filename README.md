# JubblerTechnologies

El proyecto consiste en realizar una petición de tipo GET al URL que contiene el JSON, 
el cual contiene un Array que dentro tiene objetos tipo "Casa" los cuales dentro tienen 
otro objeto que contiene las propiedades de cada tipo de moneda. Lo que hice fue usar las 
librerias de JSON y volley para poder realizar una request del Array ya mencionado. Se crea una clase tipo
Casa para poder ser instanciada dentro de un for el cual lee el Array del JSON y le setea los valores
de sus propiedades. 

En la parte visual de la aplicación use un Spinner que contiene el nombre de los objetos tipo Casa
que al ser seleccionados estos devuelven los valores de compra y venta en 2 TextView.

La parte de busqueda por fecha aun no fue desarrollada.

Dentro del codigo explico cada paso detalladamente.


