# PipePipeExtractor — Hướng dẫn cho Claude Code

## Bối cảnh dự án

Đây là **fork `dev.maxrave`** của PipePipeExtractor.

- **Origin (fork của anh):** `maxrave-dev/PipePipeExtractor`
- **Upstream (gốc):** `InfinityLoop1308/PipePipeExtractor`
- Fork đã **rename toàn bộ namespace**: `org.schabi.newpipe.extractor` → `dev.maxrave.pipepipe.extractor`
  (kèm Maven group `dev.maxrave`, proto `java_package` `dev.maxrave.pipepipe`).
- Định danh fork: version `v6.0.0`, group `dev.maxrave` (trong `build.gradle`).

## Nguyên tắc pull update từ upstream (QUAN TRỌNG)

Mỗi lần pull bản mới từ upstream `InfinityLoop1308` sẽ luôn gặp conflict, **chủ yếu do package rename**.
Khi resolve conflict, tuân thủ đúng thứ tự ưu tiên sau:

1. **Namespace LUÔN giữ `dev.maxrave.pipepipe`** — không bao giờ để sót `org.schabi.newpipe`
   (kể cả các dòng `import` nằm NGOÀI khối conflict do auto-merge kéo vào; phải quét sạch toàn repo:
   `grep -rn "org\.schabi\.newpipe" --include="*.java"` phải ra 0).
2. **Ưu tiên code của anh (HEAD)** ở chỗ logic xung khắc trực tiếp với feature riêng của fork.
3. **Nhận nâng cấp upstream** ở những file anh **chỉ rename** (không có logic riêng) — đó đúng là mục đích "pull new version".
4. **Bỏ import của dependency đã bị gỡ** (ví dụ `cache2k` đã bị upstream remove → import `org.cache2k.*` phải xoá, nếu không sẽ fail build).
5. Trước khi resolve một khối conflict, **verify symbol còn tồn tại** sau merge (method/field/constant) để tránh build gãy âm thầm — vì auto-merge có thể xoá định nghĩa mà code phía HEAD vẫn gọi.

### Feature riêng của fork cần bảo vệ
- **WEB_REMIX client** (YouTube Music) để lấy Premium audio itag 141 (AAC 256kbps) và 774 (Opus 256kbps).
  Liên quan: `YoutubeStreamExtractor` (`fetchWebRemixJsonPlayer`, `webRemixCall`, `webRemixStreamingData`),
  `ClientsConstants` (`WEB_REMIX_HARDCODED_CLIENT_VERSION`), `InnertubeClientRequestInfo`.

### Tiền lệ đã chốt (merge upstream v5.1.1, commit 924f6457)
- `YoutubeStreamExtractor.onFetchPage`: **lai** — `fetchAndroidVRJsonPlayer` (upstream) khi token rỗng
  **+** `fetchWebRemixJsonPlayer` (fork) khi có token. Giữ fix `isFetchDislike` của upstream.
- Channel tab (`YoutubeChannelTabExtractor`, `YoutubeChannelTabLinkHandlerFactory`):
  **nhận nâng cấp upstream** (channel tab sorting #62 + channel search #65), bỏ bản cũ dùng `YoutubeFilters`.
- `localization` trong `onFetchPage`: theo upstream `new Localization("en")`.

## Quy trình làm việc

- **KHÔNG tự build / compile / chạy gradle.** Anh tự build verify trong Android Studio.
  Claude chỉ verify bằng `grep`/đọc code (marker, namespace, symbol).
- Merge upstream làm trên **branch riêng** (vd `merge/upstream-vX.Y.Z`), commit trên branch,
  để anh build verify TRƯỚC, OK rồi mới đưa vào `main`.
- **Commit message thuần** — KHÔNG co-author footer, KHÔNG "Generated with Claude".
