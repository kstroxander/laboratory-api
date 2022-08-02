CREATE TABLE measurable_properties(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR_IGNORECASE(64) NOT NULL,
    description VARCHAR_IGNORECASE(256) NOT NULL,
    CONSTRAINT measurable_properties_pk PRIMARY KEY (id),
    CONSTRAINT measurable_properties_uk01 UNIQUE(name)
);

CREATE TABLE disease_risk_levels(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR_IGNORECASE(64) NOT NULL,
    description VARCHAR_IGNORECASE(256) NOT NULL,
    CONSTRAINT disease_risk_levels_pk PRIMARY KEY (id),
    CONSTRAINT disease_risk_levels_uk01 UNIQUE(name)
);

CREATE TABLE disease_types(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR_IGNORECASE(64) NOT NULL,
    description VARCHAR_IGNORECASE(256) NOT NULL,
    CONSTRAINT disease_types_pk PRIMARY KEY (id),
    CONSTRAINT disease_types_uk01 UNIQUE(name)
);

CREATE TABLE disease_type_risk_configurations(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    disease_type_id BIGINT NOT NULL,
    disease_risk_level_id BIGINT NOT NULL,
    CONSTRAINT disease_type_risk_configurations_pk PRIMARY KEY (id),
    CONSTRAINT disease_type_risk_configurations_fk01 FOREIGN KEY (disease_type_id) references disease_types (id),
    CONSTRAINT disease_type_risk_configurations_fk02 FOREIGN KEY (disease_risk_level_id) references disease_risk_levels (id),
    CONSTRAINT disease_type_risk_configurations_uk01 UNIQUE (disease_type_id, disease_risk_level_id)
);

CREATE TABLE disease_type_risk_configuration_thresholds(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    disease_type_risk_configuration_id BIGINT NOT NULL,
    measurable_property_id BIGINT NOT NULL,
    min_threshold REAL,
    max_threshold REAL,
    CONSTRAINT disease_type_risk_configuration_thresholds_pk PRIMARY KEY (id),
    CONSTRAINT disease_type_risk_configuration_thresholds_fk01 FOREIGN KEY (disease_type_risk_configuration_id) references disease_type_risk_configurations (id),
    CONSTRAINT disease_type_risk_configuration_thresholds_fk02 FOREIGN KEY (measurable_property_id) references measurable_properties (id),
    CONSTRAINT disease_type_risk_configuration_thresholds_uk01 UNIQUE (disease_type_risk_configuration_id, measurable_property_id)
);

CREATE TABLE blood_tests(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    patient_name VARCHAR_IGNORECASE(256) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by VARCHAR(100),
	modified_at  TIMESTAMP WITH TIME ZONE,
	modified_by VARCHAR(100),
    CONSTRAINT blood_tests_pk PRIMARY KEY (id)
);

CREATE TABLE blood_test_measurements(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    blood_test_id BIGINT NOT NULL,
    measured_property_id BIGINT NOT NULL,
    measured_value REAL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by VARCHAR(100),
	modified_at  TIMESTAMP WITH TIME ZONE,
	modified_by VARCHAR(100),
    CONSTRAINT blood_test_measurements_pk PRIMARY KEY (id),
    CONSTRAINT blood_test_measurements_fk01 FOREIGN KEY (blood_test_id) references blood_tests (id),
    CONSTRAINT blood_test_measurements_fk02 FOREIGN KEY (measured_property_id) references measurable_properties (id),
    CONSTRAINT blood_test_measurements_uk01 UNIQUE (blood_test_id, measured_property_id)
);

CREATE TABLE blood_test_results(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    blood_test_id BIGINT NOT NULL,
    disease_type_id BIGINT NOT NULL,
    disease_risk_level_id BIGINT,
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


