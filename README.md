# 株式会社ゆめみ Android エンジニアコードチェック課題

## 概要

株式会社ゆめみ（以下弊社）が、弊社に Android エンジニアを希望する方に出す課題のベースプロジェクト。

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="docs/app.gif" width="320">

### 環境

- IDE：Android Studio Bumblebee | 2021.1.1 Patch 3
- Kotlin：1.6.10
- Java：1.8
- Gradle：7.0.4
- minSdk：21
- targetSdk：31

### 動作

1. キーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue 数）を表示
4. 詳細をタップするとリポジトリ名のWebサイトに遷移する

### 仕様書(dokka)

仕様書の場所 ↓
/app/build/kdoc/index.html
