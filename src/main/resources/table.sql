CREATE TABLE wallets (
    id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    owner BINARY(16) NOT NULL UNIQUE,
    money INTEGER UNSIGNED NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE money_transactions (
    id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    player INTEGER UNSIGNED,
    payee INTEGER UNSIGNED,
    amount INTEGER UNSIGNED NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (player) REFERENCES wallets(id),
    FOREIGN KEY (payee) REFERENCES wallets(id)
);

CREATE VIEW current_moneys AS
SELECT
    player,
    -- 支払先として受け取った金額の合計値 - 支払った金額の合計
    (SELECT SUM(amount) FROM money_transactions AS mt2 WHERE mt2.payee = mt1.player) - SUM(amount) AS money
FROM money_transactions AS mt1
GROUP BY player;
