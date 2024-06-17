package com.talentreef.interviewquestions.takehome.services;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Service
public class WidgetService {


  private final WidgetRepository widgetRepository;

  @Autowired
  private WidgetService(WidgetRepository widgetRepository) {
    Assert.notNull(widgetRepository, "widgetRepository must not be null");
    this.widgetRepository = widgetRepository;
  }

  public List<Widget> getAllWidgets() {
    return widgetRepository.findAll();
  }

  public Widget createWidget(@Valid Widget widget) {
    if (widgetRepository.existsById(widget.getName())) {
      throw new IllegalArgumentException("A widget with the same name already exists.");
    }
    return widgetRepository.save(widget);
  }

  public Widget getWidget(@Valid String name) {
    return widgetRepository.findById(name)
            .orElseThrow(() -> new IllegalArgumentException("Widget not found with name: " + name));
  }

  public void deleteWidget(@Valid String name) {
    final Widget widget = widgetRepository.findById(name)
            .orElseThrow(() -> new IllegalArgumentException("Widget not found with name: " + name));
    widgetRepository.delete(widget);
  }

  public Widget updateWidget(@Valid Widget widget) {
    final Widget widgetDb = widgetRepository.findById(widget.getName())
            .orElseThrow(() -> new IllegalArgumentException("Widget not found with name: " + widget.getName()));
    final Widget newWidget = Widget.builder()
            .name(widgetDb.getName())
            .description(widget.getDescription())
            .price(widget.getPrice())
            .build();
    return widgetRepository.save(newWidget);
  }
}
