https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
https://developer.atlassian.com/display/CROWDDEV/Axis+1.x+Client+Stub+Generation

http://10.10.17.135:8080/bancard/pagos/brands/ms-cit/product/cuotas/invoices
http://10.10.17.135:8080/bancard/pagos/brands/ms-cit/product/cuotas/payment
http://10.10.17.135:8080/bancard/pagos/brands/ms-cit/product/cuotas/reverse

-------------------------------------------------------------
--- Carga del MicroServicio Club Internacional de Tenis
-------------------------------------------------------

curl -v -k -H 'Accept: application/json' -H "Content-Type: application/json" --noproxy localhost -X POST -d '
     {"className" : "py.com.bancard.microservice.cit.microservices.CitMSImpl",
     "status": "A", 
    "params": {
    "databaseUrl":"jdbc:mysql://200.1.203.217:3306/pagos",
"user":"citbancard",
"password":"20180212",
"driver":"com.mysql.jdbc.Driver",
"timeout":"10000"}}' http://localhost:8080/bancard/admin/services/ms-cit/cuotas


--------------------------------------------------------
--- Actualizacion del microservicio CIT ---
--------------------------------------------------------
curl -v -k -H 'Accept: application/json' -H "Content-Type: application/json" --noproxy localhost -X PUT -d '
     {"className" : "py.com.bancard.microservice.cit.microservices.CitMSImpl",
     "status": "A", 
    "params": {
    "databaseUrl":"jdbc:mysql://200.1.203.217:3306/pagos",
"user":"citbancard",
"password":"20180212",
"driver":"com.mysql.jdbc.Driver",
"timeout":"10000"}}' http://localhost:8080/bancard/admin/services/ms-cit/cuotas


--------------------------------------
--- Recarga de los micro servicios ---
--------------------------------------
curl -v -k -H 'Accept: application/json' -H "Content-Type: application/json" --noproxy localhost -X POST http://localhost:8080/bancard/admin/services/reload


-----------------------------------------------
--- Verificar Estado de los Micro-Servicios ---
-----------------------------------------------
curl -v -H 'Accept: application/json' -H "Content-Type: application/json" -X GET http://localhost:8080/bancard/admin/services/status


---------------------------------------------------------
-- Verificar Status del MicroServicio CIT ---
---------------------------------------------------------
curl -v -H 'Accept: application/json' -H "Content-Type: application/json" -X GET http://localhost:8080/bancard/admin/services/ms-cit/cuotas/status

Response:
{
  "serviceName" : "ms-cit",
  "product" : "cuotas",
  "className" : "py.com.bancard.microservice.cit.microservices.CitMSImpl",
  "status" : "A",
  "params" : {
    "driver" : "com.mysql.jdbc.Driver",
    "databaseUrl" : "jdbc:mysql://200.1.203.217:3306/pagos",
    "password" : "20180212",
    "user" : "citbancard",
    "timeout" : "10000"
  },
  "timeoutInMilliseconds" : 10000,
  "maxNumberOfFails" : 999999,
  "maxTimeOut" : 999999,
  "active" : true,
  "numberOfFails" : 0,
  "numberOfTimeout" : 0
}

-----------------------------------------------------
-- Reset Status del MicroServicio CIT Cuotas ---
-----------------------------------------------------
curl -v -H 'Accept: application/json' -H "Content-Type: application/json" -X POST http://localhost:8080/bancard/admin/services/ms-cit/cuotas/status/reset

Response:
{
  "serviceName": "ms-cit",
  "product": "cuotas",
  "className": "py.com.bancard.microservice.cit.microservices.CitMSImpl",
  "status": "A",
  "params": {
    "driver": "com.mysql.jdbc.Driver",
    "databaseUrl": "jdbc:mysql://200.1.203.217:3306/pagos",
    "password": "20180212",
    "user": "citbancard",
    "timeout": "10000"
  },
  "timeoutInMilliseconds": 10000,
  "maxNumberOfFails": 999999,
  "maxTimeOut": 999999,
  "active": true,
  "numberOfFails": 0,
  "numberOfTimeout": 0
}


----------------------------
--- Reversa de Servicios ---
----------------------------
En portal de cobranzas (10.100.19.16):
- setear la configuraciÈ¯n auto_reverse_payments en true en bancard-billing-service/current/config/service_configuration.yml
- restart del servicio: sudo /etc/init.d/mizuno_billing_service restart
- cuando se terminen las pruebas, repetir los dos pasos pero seteando la config en false

En boca web (10.100.19.116):
- setear la configuraciÈ¯n auto_reverse_payments en true en bancard-epos-service/current/config/configuration.yml
- restart del servicio: sudo /etc/init.d/mizuno_epos_service restart
- cuando se terminen las pruebas, repetir los dos pasos pero seteando la config en false
