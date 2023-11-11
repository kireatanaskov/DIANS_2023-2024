from abc import ABC, abstractmethod
import pandas as pd
import xml.etree.ElementTree as ET
import re


class Filter(ABC):
    @abstractmethod
    def process_element(self, element):
        pass

    @abstractmethod
    def apply_condition(self):
        pass

    @abstractmethod
    def get_dataset(self):
        pass

class NodeFilter(Filter):
    def __init__(self):
        self.dataset = []

    def process_element(self, element):
        element_id, latitude, longitude, name_value, category_value = get_columns(element)
        if name_value and category_value:
            data = {
                'id': element_id,
                'latitude': latitude,
                'longitude': longitude,
                'name': name_value,
                'category': category_value
            }
            self.dataset.append(data)

    def apply_condition(self):
        pass

    def get_dataset(self):
        return pd.DataFrame(self.dataset)


class WayFilter(Filter):
    def __init__(self, prev_filter):
        self.prev_filter = prev_filter
        self.dataset = []

    def process_element(self, element):
        if self.prev_filter.get_dataset().empty:
            return

        element_id, _, _, name_value, category_value = get_columns(element)
        if name_value and category_value:
            data = {
                'id': element_id,
                'name': name_value,
                'category': category_value
            }
            self.dataset.append(data)

    def apply_condition(self):
        pass

    def get_dataset(self):
        return pd.DataFrame(self.dataset)


class Pipe:
    def __init__(self, filters):
        self.filters = filters

    def process_elements(self, elements):
        for element in elements:
            for filter_instance in self.filters:
                filter_instance.process_element(element)
                filter_instance.apply_condition()

    def get_final_dataset(self):
        return self.filters[-1].get_dataset()


# Functions
def get_tag_value(element, tag_key):
    tag = element.find(f'.//tag[@k="{tag_key}"]')
    return tag.get('v') if tag is not None else None

def has_greek_letters(s):
    greek_letters_pattern = re.compile('[Α-Ωα-ω]')
    return bool(greek_letters_pattern.search(s))

def get_columns(element):
    element_id = element.get('id')
    latitude = element.get('lat')
    longitude = element.get('lon')
    name_tag = element.find('.//tag[@k="name"]')
    name_value = name_tag.get('v') if name_tag is not None else None

    if name_value is not None:
        if has_greek_letters(name_value):
            name_value = None

    category_value = ""
    amenity_value = get_tag_value(element, 'amenity')
    building_value = get_tag_value(element, 'building')
    historic_value = get_tag_value(element, 'historic')
    artwork_value = get_tag_value(element, "artwork_type")

    if amenity_value and building_value:
        category_value = amenity_value
    elif building_value:
        category_value = building_value
    elif historic_value:
        category_value = historic_value
    else:
        category_value = artwork_value

    return element_id, latitude, longitude, name_value, category_value


# Usage
xml_file_path = 'Домашна работа 1/src/Data/exportXML.osm'
with open(xml_file_path, 'r', encoding='utf-8') as file:
    xml_data = file.read()
root = ET.fromstring(xml_data)

# Create filters
node_filter = NodeFilter()
way_filter = WayFilter(node_filter)

# Create the pipe with filters in sequence
pipe_node = Pipe([node_filter])
pipe_way = Pipe([way_filter])

# Process elements through the pipe
pipe_node.process_elements(root.findall('.//node'))
pipe_way.process_elements(root.findall('.//way'))

# Get the final datasets
final_dataset_node = pipe_node.get_final_dataset()
final_dataset_way = pipe_way.get_final_dataset()

final_dataset_node.to_csv("Домашна работа 1\src\Output\Testing/final_node_data.csv", index=False)
final_dataset_way.to_csv("Домашна работа 1\src\Output\Testing/final_way_data.csv", index=False)
