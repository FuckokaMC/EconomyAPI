# EconomyAPI

既存の経済プラグインと異なり、取引履歴を基とするプラグイン  
所持金も取引履歴から算出する。  
**趣味性高め 実用性皆無**  
Vault対応(多分)

## コンセプト(?)

既存の経済プラグインのデータ構造は以下のようなものとなり、単純にプレイヤーに紐づく数値を取得する。  
データ構造的に`/money set`が可能であり、経済の整合性が破綻しがち。  
所持金の遷移も見れないため、自分の所持金がどういう経緯でそうなっているのかも不明。  
(`/money set`は権限管理すればよいし、そもそも`/money give`とか`/money take`とかもあるやん！  
所持金の遷移もその経済プラグインに対応した履歴プラグイン作ればいいやん！　と言われればぐうの音も出ないが)

| プレイヤー | 所持金 |
| --- | --- |
| deceitya | 1000 |
| deceitya_sub | 2000 |
| ceityan | 500 |

対してこのプラグインは以下のようなものとなり、`所持金 = 受取金額の合計 - 支払金額の合計`で計算される。  
支払側と受取側のプレイヤー、取引金額とその理由を記録することで、誰に支払っただとかいつ受け取った等、  
自分の所持金のソースが分かる。(家計簿的な感じ)  
  
`/money give` `/money take`コマンドは用意されているので結局経済の破綻は避けられないが...  
本当はサーバーのアカウントを用意して、サーバーのアカウントからgive/takeの金額が捻出されるようにしたい。  
~~実用性がマジで皆無になりそう&めんどくさいからやらないけど~~  

| 支払側 | 受取側 | 取引金額 | 理由 | 時刻 |
| --- | --- | --- | --- | --- |
| deceitya | deceitya_sub | 10000 | チェストショップで木材100個購入 | 1919-08-10 11:45:14 |
| deceitya | ceityan | 6000 | 食事代 | 2025-01-01 00:00:00 |
| decietya_sub | deceitya | 30000 | イベントの賞金 | 2025-01-02 12:34:56 |

# コマンド

| コマンド | 説明 |
| --- | --- |
| /money | 自分の所持金を表示する |
| /money [プレイヤー名] | プレイヤーの所持金を表示する |
| /money pay <プレイヤー名> <金額> | プレイヤーにお金を払う |
| /money give <プレイヤー名> <金額> | プレイヤーの所持金を増やす |
| /money take <プレイヤー名> <金額> | プレイヤーの所持金を減らす |
| /money log [ページ数] | 自分の所持金の履歴を表示する |
| /money log <プレイヤー名> [ページ数] | プレイヤーの所持金の履歴を表示する |
| /money help | コマンド一覧を表示する |
