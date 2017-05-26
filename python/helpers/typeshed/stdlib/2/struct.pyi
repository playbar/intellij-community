# Stubs for struct for Python 2.7
# Based on https://docs.python.org/2/library/struct.html

from typing import Any, Tuple

class error(Exception): ...

def pack(fmt: str, *v: Any) -> str: ...
# TODO buffer type
def pack_into(fmt: str, buffer: Any, offset: int, *v: Any) -> None: ...

# TODO buffer type
def unpack(fmt: str, buffer: Any) -> Tuple[Any, ...]: ...
def unpack_from(fmt: str, buffer: Any, offset: int = ...) -> Tuple[Any, ...]: ...

def calcsize(fmt: str) -> int: ...

class Struct:
    format = ...  # type: str
    size = ...  # type: int

    def __init__(self, format: str) -> None: ...

    def pack(self, *v: Any) -> str: ...
    # TODO buffer type
    def pack_into(self, buffer: Any, offset: int, *v: Any) -> None: ...
    def unpack(self, buffer: Any) -> Tuple[Any, ...]: ...
    def unpack_from(self, buffer: Any, offset: int = ...) -> Tuple[Any, ...]: ...
