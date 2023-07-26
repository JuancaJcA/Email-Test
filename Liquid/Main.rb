require 'json'
require 'liquid'

def load_data
  # Read JSON
  data_file = File.read('Sample_data.json')
  JSON.parse(data_file)
end

def generate_html(data)
  # Liquid Template
  template_file = File.read('ThirdTask.liquid')
  template = Liquid::Template.parse(template_file)
  rendered_template = template.render(data)

  # Save to HTML
  File.open('output.html', 'w') do |file|
    file.write(rendered_template)
  end
end

# Load data
data = load_data()

# Generate HTML
generate_html(data)
