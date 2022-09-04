FROM postgres:14

RUN mkdir -p /usr/local/docs/pgdata; \
    mkdir -p /usr/local/docs/pgindex; \

    chown -R postgres:postgres /usr/local; \
    chmod -R u+rwx /usr/local

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=docs

HEALTHCHECK --interval=10s --timeout=5s --retries=5 --start-period=50s CMD psql -U postgres -h 0.0.0.0 -p 5432 docs