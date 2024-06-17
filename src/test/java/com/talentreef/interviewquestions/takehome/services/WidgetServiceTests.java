package com.talentreef.interviewquestions.takehome.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

@RunWith(SpringRunner.class)
public class WidgetServiceTests {

  @Mock
  private WidgetRepository widgetRepository;

  @InjectMocks
  private WidgetService widgetService;

  @Test
  public void when_getAllWidgets_expect_findAllResult() {
    final Widget widget = Widget.builder()
            .name("Widgette Nielson")
            .description("Test widget")
            .price(BigDecimal.valueOf(9.99))
            .build();
    List<Widget> response = List.of(widget);
    when(widgetRepository.findAll())
            .thenReturn(response);

    List<Widget> result = widgetService.getAllWidgets();
    assertThat(result).isEqualTo(response);
  }

  @Test
  public void createWidgetTest() {
    final Widget widget = Widget.builder()
            .name("Widgette Nielson")
            .description("Test widget")
            .price(BigDecimal.valueOf(9.99))
            .build();
    when(widgetRepository.existsById(widget.getName()))
            .thenReturn(false);
    when(widgetRepository.save(widget))
            .thenReturn(widget);

    final Widget createdWidget = widgetService.createWidget(widget);

    assertNotNull(createdWidget);
    assertEquals(widget.getName(), createdWidget.getName());
  }

  @Test
  public void createWidgetWithExceptionIfWidgetExists() {
    final Widget widget = Widget.builder()
            .name("Widgette Nielson")
            .description("Test widget")
            .price(BigDecimal.valueOf(9.99))
            .build();
    when(widgetRepository.existsById(widget.getName())).thenReturn(true);

    assertThrows(IllegalArgumentException.class,
            () -> widgetService.createWidget(widget));

  }

  @Test
  public void getWidgetTest() {
    final Widget widget = Widget.builder()
            .name("Widgette Nielson")
            .description("Test widget")
            .price(BigDecimal.valueOf(9.99))
            .build();
    when(widgetRepository.findById(widget.getName()))
            .thenReturn(Optional.of(widget));

    final Widget foundWidget = widgetService.getWidget(widget.getName());

    assertNotNull(foundWidget);
    assertEquals(widget.getName(), foundWidget.getName());
  }

  @Test
  public void getWidgetWithExceptionIfWidgetNotFound() {
    final Widget widget = Widget.builder()
            .name("Widgette Nielson")
            .description("Test widget")
            .price(BigDecimal.valueOf(9.99))
            .build();
    when(widgetRepository.findById(widget.getName()))
            .thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class,
            () -> widgetService.getWidget(widget.getName()));

  }

  @Test
  public void deleteWidgetTest() {
    final Widget widget = Widget.builder()
            .name("Widgette Nielson")
            .description("Test widget")
            .price(BigDecimal.valueOf(9.99))
            .build();
    when(widgetRepository.findById(widget.getName()))
            .thenReturn(Optional.of(widget));

    widgetService.deleteWidget(widget.getName());

    verify(widgetRepository, times(1)).findById(widget.getName());
    verify(widgetRepository, times(1)).delete(widget);
  }

  @Test
  public void deleteWidgetWithExceptionIfWidgetNotFound() {
    final Widget widget = Widget.builder()
            .name("Widgette Nielson")
            .description("Test widget")
            .price(BigDecimal.valueOf(9.99))
            .build();
    when(widgetRepository.findById(widget.getName()))
            .thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class,
            () -> widgetService.deleteWidget(widget.getName()));

  }

  @Test
  public void updateWidgetTest() {
    final Widget widget = Widget.builder()
            .name("Widgette Nielson")
            .description("Test widget")
            .price(BigDecimal.valueOf(9.99))
            .build();
    when(widgetRepository.findById(widget.getName()))
            .thenReturn(Optional.of(widget));
    when(widgetRepository.save(any(Widget.class)))
            .thenReturn(widget);

    widget.setDescription("Updated Description");
    widget.setPrice(BigDecimal.valueOf(200.00));

    Widget updatedWidget = widgetService.updateWidget(widget);

    assertNotNull(updatedWidget);
    assertEquals(widget.getDescription(), updatedWidget.getDescription());
    assertEquals(widget.getPrice(), updatedWidget.getPrice());
  }

  @Test
  public void updateWidgetWithExceptionIfWidgetNotFound() {
    final Widget widget = Widget.builder()
            .name("Widgette Nielson")
            .description("Test widget")
            .price(BigDecimal.valueOf(9.99))
            .build();
    when(widgetRepository.findById(widget.getName()))
            .thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class,
            () -> widgetService.updateWidget(widget));
  }
}
