`ex2_buffered_reader` and `ex2_primitive_types` solve the same calculator problem with two transport styles:

- `ex2_buffered_reader`: values are sent as text lines, then parsed on the server with `Double.parseDouble(...)`.
- `ex2_primitive_types`: values are sent in binary form with `DataOutputStream` / `DataInputStream`, so no string parsing is needed for the numbers.
