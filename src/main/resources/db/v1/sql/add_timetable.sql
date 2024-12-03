CREATE TABLE t_timetable (
    id            BIGSERIAL PRIMARY KEY,
    created  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    updated  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    start_at TIME WITHOUT TIME ZONE NOT NULL,
    end_at TIME WITHOUT TIME ZONE NOT NULL,
    day_of_week TEXT NOT NULL,
    specified_day DATE
);

INSERT INTO t_timetable (start_at,end_at,day_of_week) VALUES
     ('10:00','18:00','MONDAY'),
     ('10:00','18:00','TUESDAY'),
     ('10:00','18:00','WEDNESDAY'),
     ('10:00','18:00','THURSDAY'),
     ('10:00','18:00','FRIDAY'),
     ('10:00','16:00','SATURDAY');

INSERT INTO t_timetable (start_at,end_at,day_of_week,specified_day) VALUES ('10:00','18:00','WEDNESDAY','2024-12-04');