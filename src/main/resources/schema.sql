CREATE TABLE measurable_properties COMMENT 'Almacena la maestra de propiedades medibles'(
    id BIGINT GENERATED ALWAYS AS IDENTITY COMMENT 'Llave primaria que identifica de manera unica na propiedad medible',
    name VARCHAR_IGNORECASE(64) NOT NULL COMMENT 'Nombre simple asignado a una propiedad medible',
    description VARCHAR_IGNORECASE(256) NOT NULL COMMENT 'Descripción detallada que explica el significado de una propiedad medible',
    CONSTRAINT measurable_properties_pk PRIMARY KEY (id),
    CONSTRAINT measurable_properties_uk01 UNIQUE(name)
);

CREATE TABLE disease_risk_levels COMMENT 'Almacena la maestra de niveles de riesgo de enfermedad'(
    id BIGINT GENERATED ALWAYS AS IDENTITY COMMENT 'Llave primaria que identifica de manera unica un nivel de riesgo',
    name VARCHAR_IGNORECASE(64) NOT NULL COMMENT 'Nombre simple asignado a un nivel de riesgo',
    description VARCHAR_IGNORECASE(256) NOT NULL COMMENT 'Descripción detallada que explica el significado de un nivel de riesgo',
    CONSTRAINT disease_risk_levels_pk PRIMARY KEY (id),
    CONSTRAINT disease_risk_levels_uk01 UNIQUE(name)
);

CREATE TABLE disease_types COMMENT 'Almacena la maestra de tipos de enfermedad'(
    id BIGINT GENERATED ALWAYS AS IDENTITY COMMENT 'Llave primaria que identifica de manera unica un tipo enfermedad',
    name VARCHAR_IGNORECASE(64) NOT NULL COMMENT 'Nombre comun asignado a un tipo enfermedad',
    description VARCHAR_IGNORECASE(256) NOT NULL COMMENT 'Nombre extendido de un tipo enfermedad',
    CONSTRAINT disease_types_pk PRIMARY KEY (id),
    CONSTRAINT disease_types_uk01 UNIQUE(name)
);

CREATE TABLE disease_type_risk_configurations COMMENT 'Almacena la definicion de niveles de riesgo por enfermedad basada en limites de azucar, grasa y oxigeno'(
    id BIGINT GENERATED ALWAYS AS IDENTITY COMMENT 'Llave primaria que identifica de manera unica una definicion de niveles de riesgo por enfermedad',
    disease_type_id BIGINT NOT NULL COMMENT 'Id del tipo de enfermedad para la cual aplica un nivel de riesgo',
    disease_risk_level_id BIGINT NOT NULL COMMENT 'Id del nivel de riesgo para el cual aplica un conjunto de limites',
    CONSTRAINT disease_type_risk_configurations_pk PRIMARY KEY (id),
    CONSTRAINT disease_type_risk_configurations_fk01 FOREIGN KEY (disease_type_id) references disease_types (id),
    CONSTRAINT disease_type_risk_configurations_fk02 FOREIGN KEY (disease_risk_level_id) references disease_risk_levels (id),
    CONSTRAINT disease_type_risk_configurations_uk01 UNIQUE (disease_type_id, disease_risk_level_id)
);

CREATE TABLE disease_type_risk_configuration_thresholds COMMENT 'Almacena la configuracion de limites para un tipo enfermedad y nivel de riesgo'(
    id BIGINT GENERATED ALWAYS AS IDENTITY COMMENT 'Llave primaria que identifica de manera unica un conjunto de limites',
    disease_type_risk_configuration_id BIGINT NOT NULL COMMENT 'Id de la definicion de niveles de riesgo por enfermedad',
    measurable_property_id BIGINT NOT NULL COMMENT 'Id de la propiedad medible para la cual aplican los limites',
    min_threshold REAL COMMENT 'Limite inferior en nivel configurado para una propiedad medible',
    max_threshold REAL COMMENT 'Limite superior en nivel configurado para una propiedad medible',
    CONSTRAINT disease_type_risk_configuration_thresholds_pk PRIMARY KEY (id),
    CONSTRAINT disease_type_risk_configuration_thresholds_fk01 FOREIGN KEY (disease_type_risk_configuration_id) references disease_type_risk_configurations (id),
    CONSTRAINT disease_type_risk_configuration_thresholds_fk02 FOREIGN KEY (measurable_property_id) references measurable_properties (id),
    CONSTRAINT disease_type_risk_configuration_thresholds_uk01 UNIQUE (disease_type_risk_configuration_id, measurable_property_id)
);

CREATE TABLE blood_tests COMMENT 'Almacena los examenes de sangre'(
    id BIGINT GENERATED ALWAYS AS IDENTITY COMMENT 'Llave primaria que identifica de manera unica el registro de un examen de sangre',
    patient_name VARCHAR_IGNORECASE(256) NOT NULL COMMENT 'Nombre del paciente cuyo examen de sangre se registra',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Fecha de registro de un examen de sangre con fines de auditoria',
	created_by VARCHAR(100) COMMENT 'Username de quien regstró un examen de sangre con fines de auditorio',
	modified_at  TIMESTAMP WITH TIME ZONE COMMENT 'Fecha mas reciente de modificacion de un examen de sangre con fines de auditoria',
	modified_by VARCHAR(100) COMMENT 'Username de quien modificó recientemente un examen de sangre con fines de auditoria',
    CONSTRAINT blood_tests_pk PRIMARY KEY (id)
);

CREATE TABLE blood_test_measurements COMMENT 'Almacena las mediciones obtenidas en examenes de sangre'(
    id BIGINT GENERATED ALWAYS AS IDENTITY COMMENT 'Llave primaria que identifica de manera unica uan medición',
    blood_test_id BIGINT NOT NULL COMMENT 'Id del examen de sangre al cual está asociada una medición',
    measured_property_id BIGINT NOT NULL COMMENT 'Id de la propiedad medible para la cual aplica una medición',
    measured_value REAL COMMENT 'Valor medido para una propiedad en un examen de sangre',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Fecha de registro de una medición',
	created_by VARCHAR(100) COMMENT 'Username de quien registro de una medición',
	modified_at  TIMESTAMP WITH TIME ZONE COMMENT 'Fecha mas reciente de modificacion de una medición',
	modified_by VARCHAR(100) COMMENT 'Username de quien modificó recientemente una medición',
    CONSTRAINT blood_test_measurements_pk PRIMARY KEY (id),
    CONSTRAINT blood_test_measurements_fk01 FOREIGN KEY (blood_test_id) references blood_tests (id),
    CONSTRAINT blood_test_measurements_fk02 FOREIGN KEY (measured_property_id) references measurable_properties (id),
    CONSTRAINT blood_test_measurements_uk01 UNIQUE (blood_test_id, measured_property_id)
);

CREATE TABLE blood_test_results COMMENT 'Almacena los resultados obtenidos con base en las medidas ingresadas en examentes de sangre'(
    id BIGINT GENERATED ALWAYS AS IDENTITY COMMENT 'Llave primaria que identifica de manera unica un resultado final de un examen de sangre',
    blood_test_id BIGINT NOT NULL COMMENT 'Id del examen de sangre para el cual aplica un resultado',
    disease_type_id BIGINT NOT NULL COMMENT 'Id del tipo de enfermedad para la cual aplica el resultado de un examen de sangre',
    disease_risk_level_id BIGINT COMMENT 'Id del nivel de riesgo calculado como resultado de un examen de sangre',
    CONSTRAINT blood_test_results_pk PRIMARY KEY (id),
    CONSTRAINT blood_test_results_fk01 FOREIGN KEY (blood_test_id) references blood_tests (id),
    CONSTRAINT blood_test_results_fk02 FOREIGN KEY (disease_type_id) references disease_types (id),
    CONSTRAINT blood_test_results_fk03 FOREIGN KEY (disease_risk_level_id) references disease_risk_levels (id),
    CONSTRAINT blood_test_results_uk01 UNIQUE (blood_test_id, disease_type_id)
);


INSERT INTO measurable_properties(name, description) VALUES
('Porcentaje Azúcar', 'Porcentaje de Azúcar medido en examen de laboratorio practicado a un paciente'),
('Porcentaje Grasa', 'Porcentaje de Grasa medido en examen de laboratorio practicado a un paciente'),
('Porcentaje Oxigeno', 'Porcentaje de Oxigeno medido en examen de laboratorio practicado a un paciente');

INSERT INTO disease_risk_levels(name, description) VALUES
('BAJO', 'Nivel de riesgo que representa una probabilidad minima de padecer gravemente determinada enfermedad'),
('MEDIO', 'Nivel de riesgo que representa una probabilidad media de padecer gravemente determinada enfermedad'),
('ALTO', 'Nivel de riesgo que representa una probabilidad inminente de padecer gravemente determinada enfermedad');

INSERT INTO disease_types(name, description) VALUES
('Diabetes', 'Es una enfermedad prolongada (crónica) en la cual el cuerpo no puede regular la cantidad de Azúcar en la sangre');

INSERT INTO disease_type_risk_configurations(disease_type_id, disease_risk_level_id) VALUES
((SELECT id FROM disease_types WHERE name = 'Diabetes'), (SELECT id FROM disease_risk_levels WHERE name = 'BAJO')),
((SELECT id FROM disease_types WHERE name = 'Diabetes'), (SELECT id FROM disease_risk_levels WHERE name = 'MEDIO')),
((SELECT id FROM disease_types WHERE name = 'Diabetes'), (SELECT id FROM disease_risk_levels WHERE name = 'ALTO'));

INSERT INTO disease_type_risk_configuration_thresholds(disease_type_risk_configuration_id, measurable_property_id, min_threshold, max_threshold) VALUES
((SELECT cfg.id FROM disease_type_risk_configurations cfg JOIN disease_types dt ON cfg.disease_type_id = dt.id JOIN disease_risk_levels rl ON cfg.disease_risk_level_id = rl.id WHERE dt.name = 'Diabetes' AND rl.name =  'ALTO'), (SELECT id FROM measurable_properties WHERE name = 'Porcentaje Azúcar'), 0.71, NULL),
((SELECT cfg.id FROM disease_type_risk_configurations cfg JOIN disease_types dt ON cfg.disease_type_id = dt.id JOIN disease_risk_levels rl ON cfg.disease_risk_level_id = rl.id WHERE dt.name = 'Diabetes' AND rl.name =  'ALTO'), (SELECT id FROM measurable_properties WHERE name = 'Porcentaje Grasa'), 0.886, NULL),
((SELECT cfg.id FROM disease_type_risk_configurations cfg JOIN disease_types dt ON cfg.disease_type_id = dt.id JOIN disease_risk_levels rl ON cfg.disease_risk_level_id = rl.id WHERE dt.name = 'Diabetes' AND rl.name =  'ALTO'), (SELECT id FROM measurable_properties WHERE name = 'Porcentaje Oxigeno'), NULL, 0.59),

((SELECT cfg.id FROM disease_type_risk_configurations cfg JOIN disease_types dt ON cfg.disease_type_id = dt.id JOIN disease_risk_levels rl ON cfg.disease_risk_level_id = rl.id WHERE dt.name = 'Diabetes' AND rl.name =  'MEDIO'), (SELECT id FROM measurable_properties WHERE name = 'Porcentaje Azúcar'), 0.5, 0.7),
((SELECT cfg.id FROM disease_type_risk_configurations cfg JOIN disease_types dt ON cfg.disease_type_id = dt.id JOIN disease_risk_levels rl ON cfg.disease_risk_level_id = rl.id WHERE dt.name = 'Diabetes' AND rl.name =  'MEDIO'), (SELECT id FROM measurable_properties WHERE name = 'Porcentaje Grasa'), 0.622, 0.885),
((SELECT cfg.id FROM disease_type_risk_configurations cfg JOIN disease_types dt ON cfg.disease_type_id = dt.id JOIN disease_risk_levels rl ON cfg.disease_risk_level_id = rl.id WHERE dt.name = 'Diabetes' AND rl.name =  'MEDIO'), (SELECT id FROM measurable_properties WHERE name = 'Porcentaje Oxigeno'), 0.6, 0.7),

((SELECT cfg.id FROM disease_type_risk_configurations cfg JOIN disease_types dt ON cfg.disease_type_id = dt.id JOIN disease_risk_levels rl ON cfg.disease_risk_level_id = rl.id WHERE dt.name = 'Diabetes' AND rl.name =  'BAJO'), (SELECT id FROM measurable_properties WHERE name = 'Porcentaje Azúcar'), NULL, 0.499),
((SELECT cfg.id FROM disease_type_risk_configurations cfg JOIN disease_types dt ON cfg.disease_type_id = dt.id JOIN disease_risk_levels rl ON cfg.disease_risk_level_id = rl.id WHERE dt.name = 'Diabetes' AND rl.name =  'BAJO'), (SELECT id FROM measurable_properties WHERE name = 'Porcentaje Grasa'), NULL, 0.6199),
((SELECT cfg.id FROM disease_type_risk_configurations cfg JOIN disease_types dt ON cfg.disease_type_id = dt.id JOIN disease_risk_levels rl ON cfg.disease_risk_level_id = rl.id WHERE dt.name = 'Diabetes' AND rl.name =  'BAJO'), (SELECT id FROM measurable_properties WHERE name = 'Porcentaje Oxigeno'), NULL, 0.69);
