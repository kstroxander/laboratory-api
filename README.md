# laboratoy-ui
Repositorio que contiene el codigo fuente para reto tecnico realizado en el proceso de seleccion con i4digital.

## Stack

* Java 1.8
* Spring Boot
* Webflux
* R2DBC
* H2 Database
* JOOQ
* Lombok
* MapStruct

## Modelo

* MeasurableProperty: Clase que representa un atributo / analito / paraclinico humano medible (Ej: % de azucar en sangre, % de oxigeno)
  * id: Identificador unico de un atributo medible
  * name: Nombre corto de un atributo medible
  * description: Desccripción detallada sobre un atributo medible

* DiseaseType: Clase que representa un tipo de enfermedad
  * id: Identificador unico de un tipo enfermedad
  * name: Nombre corto de un tipo enfermedad
  * description: Desccripción detallada sobre un tipo enfermedad

* DiseaseRiskLevel: Clase que representa un nivel de riesgo de enfermedad
  * id: Identificador unico de un nivel de riesgo
  * name: Nombre corto de un nivel de riesgo
  * description: Desccripción detallada sobre un nivel de riesgo

* BloodTest: Clase que representa la informaci&oacute;n de un examen de sangre
  * id: Identificador unico de un examen de sangre
  * patientName: Nombre del paciente a quien se le practica un examen
  * createdBy: Atributo con proposito de auditoria, el cual hace referencia al usuario que registra un examen de sangre
  * createdAt: Atributo con proposito de auditoria, el cual hace referencia la fecha de registro de un examen de sangre
  * modifiedBy: Atributo con proposito de auditoria, el cual hace referencia al usuario que recientemente modific&oacute; un examen de sangre
  * modifiedAt: Atributo con proposito de auditoria, el cual hace referencia la fecha mas reciente en que se modificó un examen de sangre

* BloodTestMeasurement: Clase que representa las lecturas ingresadas al momento de registrar un examen de sangre (Porcentaje Azucar: 10)
  * id: Identificador unico de un registro de medici&oacute;n
  * measuredProperty: Objeto que representa a un atributo medible del ser humano
  * testId: Apuntador que hace referencia al examen de sangre al cual pertenece una medici&oacute;n
  * measuredValue: Valor leido para determinado atributo dentro de un examen de sangre
  * createdBy: Atributo con proposito de auditoria, el cual hace referencia al usuario que registra las mediciones de un examen de sangre
  * createdAt: Atributo con proposito de auditoria, el cual hace referencia la fecha de registro de una medicion
  * modifiedBy: Atributo con proposito de auditoria, el cual hace referencia al usuario que recientemente modific&oacute; la medicion
  * modifiedAt: Atributo con proposito de auditoria, el cual hace referencia la fecha mas reciente en que se modificó una medici&oacute;n

* BloodTestResult: Clase que representa un resultado, producto de el ingreso de mediciones y la ejecución del proceso calculo del riesgo
  * Id: Identificador unico de un registro de un resultado
  * testId: Identificado del examen de sangre al cual pertenece un resultado
  * diseaseType: Enfermedad para la cual se calcula un nivel de riesgo
  * riskLevel: Indica el nivel de riesgo de una enfernedad, calculado a partir de un conjunto de lecturas 

* DiseaseTypeRiskConfiguration: Clase que representa la configuraci&oacute;n de reglas para determinar niveles de riesgo por enfermedad
  * Id: Identificador unico de una configuracion de nivel de riesgo
  * diseaseType: Tipo de enfermedad para la cual aplica una configuracion de reglas
  * riskLevel: Nivel de riesgo relacionado con un conjunto de reglas

* DiseaseTypeRiskConfigurationThreshold: Clase que almacena lo configuracion de limites con base en los cuales se calculan los niveles de riesgo
* configurationId: Identificador que apunta hacia el registro de configuracion,que agrupa un rango de limites
* measurableProperty: Propiedad medible, sobre la cual se especifican los limites a considerar en una regla de niveles de riesgo
* minThreshold: Define el valor minimo de la propiedad medible indicada en la regla
* minThreshold: Define el valor maximo de la propiedad medible indicada en la regla











