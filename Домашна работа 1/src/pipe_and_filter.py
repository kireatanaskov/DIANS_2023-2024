# import pandas as pd
# import xml.etree.ElementTree as ET
# import re


class Pipe:
    def __init__(self, data):
        self.data = data

    def process(self, filter_func):
        return Pipe(filter_func(self.data))

